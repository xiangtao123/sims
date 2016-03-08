package com.jsrush.security.rbac.service;

import com.jsrush.security.rbac.entity.Role;
import com.jsrush.security.rbac.entity.User;
import com.jsrush.security.rbac.repository.RoleDao;
import com.jsrush.security.rbac.repository.UserDao;
import com.jsrush.security.rbac.vo.UserVO;
import com.jsrush.util.Digests;
import com.jsrush.util.Encodes;
import com.jsrush.util.StringHelper;
import com.jsrush.util.SystemUtil;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户管理类.
 * 
 * @author sunburst
 */
@Service
@Transactional(readOnly = true)
public class UserService {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;

	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserDao userDao;

	@Autowired
	private RoleService roleService;

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private ShiroManager shiroManager;

	public List<User> findListWithParams(String params, int index, int pageSize, String permissions) {
		return userDao.getListWithParams(params, index, pageSize, permissions);
	}

	public Long findCountWithParams(String params, String permissions) {
		return userDao.getCountWithParams(params, permissions);
	}

	public List<User> getAllUser() {
		return (List<User>) userDao.findAll();
	}

	public User getUser(Long id) {
		return userDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public void saveUser(UserVO userVO) {
		User user = new User();
		user = userDao.findOne(userVO.getId());
		user.setAreaInformation(userVO.getAreaInformation());
		user.setCompanyFax(userVO.getCompanyFax());
		user.setCompanyName(userVO.getCompanyName());
		user.setCompanyPhone(userVO.getCompanyPhone());
		user.setEmail(userVO.getEmail());
		user.setName(userVO.getName());

		userDao.save(user);

	}

	public User findUserByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}

	@Transactional(readOnly = false)
	public void registerUser(Role role, String loginName, String userName, String plainPassword, String email) {
		User user = new User();
		user.setLoginName(loginName);
		user.setName(userName);
		user.setPlainPassword(plainPassword);
		user.setRole(role);
		entryptPassword(user);
		user.setRegisterDate(new Date());
		user.setEmail(email);
		// 保存user permissions
		for (Role rl : role.getRoles()) {
			user.getRoles().add(rl);
		}
		userDao.save(user);
	}

	@Transactional(readOnly = false)
	public void updateUser(User user) {
		if (StringUtils.isNotBlank(user.getPlainPassword())) {
			entryptPassword(user);
		}
		userDao.save(user);
	}

	@Transactional(readOnly = false)
	public void deleteUser(String params) throws Exception {
		String[] idArray = params.split(",");
		for (String idStr : idArray) {
			if (StringHelper.isEmpty(idStr))
				continue;
			if (isSupervisor(Long.valueOf(idStr))) {
				logger.warn("操作员{}尝试删除超级管理员用户", idStr);
				throw new Exception("不能删除超级管理员用户");
			} else {
				userDao.delete(Long.valueOf(idStr));
			}
		}
	}

	@Transactional(readOnly = false)
	public int changePwd(String params) {
		JSONObject userDto = SystemUtil.fromObject(params);
		if (userDto != null) {
			User newUser = userDao.findOne(userDto.getLong("id"));
			newUser.setPlainPassword(userDto.getString("plainPassword"));
			entryptPassword(newUser);
			userDao.save(newUser);
			return 1;
		} else {
			return 0;
		}
	}

	@Transactional(readOnly = false)
	public void save(User user) {
		userDao.save(user);
	}

	@Transactional(readOnly = false)
	public int saveUser(String params) {
		logger.info("save user info :" + params);
		JSONObject userDTO = SystemUtil.fromObject(params);
		String roleIdStr = userDTO.getString("role");
		if (StringHelper.isEmpty(roleIdStr))
			return 0;
		Role role = roleService.getRole(Long.valueOf(roleIdStr));
		User newUser = (User) JSONObject.toBean(userDTO, User.class);
		User user = userDao.findOne(newUser.getId());
		user.setLoginName(newUser.getLoginName());
		user.setName(newUser.getName());
		user.setEmail(newUser.getEmail());
		if (newUser.getPassword() != null)
			user.setPlainPassword(newUser.getPassword());
		user.setRole(role);
		user.getRoles().clear();
		user.getRoles().addAll(role.getRoles());

		entryptPassword(user);
		userDao.save(user);
		return 1;
	}

	@Transactional(readOnly = false)
	public int updateUser(String params) {
		JSONObject userDTO = SystemUtil.fromObject(params);
		User newUser = userDao.findOne(userDTO.getLong("id"));
		String roleIdStr = userDTO.getString("roleId");
		if (StringHelper.isEmpty(roleIdStr))
			return 0;
		Role role = roleService.getRole(Long.valueOf(roleIdStr));
		newUser.setName(userDTO.getString("name"));
		// newUser.setPlainPassword(userDTO.getString("plainPassword"));
		newUser.setRole(role);
		// entryptPassword(newUser);
		userDao.save(newUser);
		return 1;
	}

	@Transactional(readOnly = false)
	public int deleteRole(Long id) {
		Role role = roleService.getRole(id);
		if (role == null)
			return 0;
		boolean hasUser = CollectionUtils.isNotEmpty(role.getUser());
		if (hasUser)
			return 2;
		Role parentRole = role.getParentRole();
		Set<Role> childRole = role.getChildRole();
		Role[] roles = childRole.toArray(new Role[childRole.size()]);
		for (Role rl : roles) {
			rl.setParentRole(parentRole);
			roleDao.save(rl);
		}
		roleService.del(id);
		return 1;
	}

	/**
	 * 判断是否超级管理员.
	 */
	private boolean isSupervisor(Long id) {
		User user = userDao.findOne(id);
		if (user != null) {
			Role role = user.getRole();
			if (role != null && role.getParentRole() == null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取当前登陆者的角色和上级角色的roleId collections.
	 * 
	 * @param roleIds
	 * @param role
	 */
	public void findAboveRoleIds(Set<Long> roleIds, Role role) {
		if (role == null)
			return;
		roleIds.add(role.getId());
		findAboveRoleIds(roleIds, role.getParentRole());
	}

	/**
	 * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
	 */
	public void entryptPassword(User user) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		user.setSalt(Encodes.encodeHex(salt));

		byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
		user.setPassword(Encodes.encodeHex(hashPassword));
	}

	/**
	 * 加密字符串
	 * 
	 * @param srcStr
	 * @param saltStr
	 * @return
	 */
	public String entryptPassword(String srcStr, String saltStr) {
		byte[] salt = Encodes.decodeHex(saltStr);
		byte[] hashPassword = Digests.sha1(srcStr.getBytes(), salt,
				HASH_INTERATIONS);
		return Encodes.encodeHex(hashPassword);
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public Set<User> findListByLoginId(String loginIds) {
		Set<User> users = new HashSet<User>();
		if (StringHelper.isEmpty(loginIds)) {
			return users;
		}

		String loginNameList = " '" + loginIds.replaceAll(",", "', '") + "'";
		List<User> list = userDao.findListByLoginId(loginNameList);
		for (User u : list) {
			users.add(u);
		}
		return users;
	}

	public List<User> loadList(String permissions) {
		return userDao.loadList(permissions);
	}

	public List<User> findByRole(Role role) {
		return userDao.findByRole(role);
	}
}
