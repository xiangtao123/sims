package com.jsrush.security.rbac.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsrush.security.rbac.entity.Action;
import com.jsrush.security.rbac.entity.Ec;
import com.jsrush.security.rbac.entity.Role;
import com.jsrush.security.rbac.entity.User;
import com.jsrush.security.rbac.repository.ActionDao;
import com.jsrush.security.rbac.repository.EcDao;
import com.jsrush.security.rbac.repository.RoleDao;
import com.jsrush.util.StringHelper;
import com.jsrush.util.SystemUtil;

@Service
@Transactional(readOnly = true)
public class RoleService {

	private static final String TREE_ID_PREFIX = "role_";

	@Autowired
	private RoleDao roleDao;

	@Autowired
	private ActionDao actionDao;

	@Autowired
	private EcDao ecDao;

	@Transactional(readOnly = false)
	public void del(Long id) {
		Role role = roleDao.findOne(id);
		if (role == null)
			return;

		for (Role role1 : role.getChildRole()) {
			role1.setParentRole(role.getParentRole());
			roleDao.save(role1);
		}

		roleDao.delete(id);
	}

	/**
	 * 给项目添加permissions
	 */
	public Set<Role> getRolesToOther(Long[] roleIds) {
		Set<Role> roles = new HashSet<Role>();
		int len = roleIds.length;
		for (int n = 0; n < len; n++) {
			Role role = roleDao.findOne(roleIds[n]);
			if (role != null)
				roles.add(role);
		}
		return roles;
	}

	public Set<Role> getRolesToOther(List<Long> roleIds) {
		return getRolesToOther(roleIds.toArray(new Long[roleIds.size()]));
	}

	/**
	 * 获得最大的管理角色
	 */
	public Role getparentRole() {
		Role parentRole = roleDao.getNullpId();
		return parentRole;
	}

	@Transactional(readOnly = false)
	public Role saveByRoleName(String roleName, Long ecId, Long[] permissions) {
		Role parentRole = roleDao.getNullpId();
		Role role = new Role();
		role.setRoleName(roleName);
		role.setParentRole(parentRole);
		for (Long rId : permissions) {
			Role rl = roleDao.findOne(rId);
			if (rl == null)
				continue;
			role.getRoles().add(rl);
		}
		setEcAndRoleRecoed(role, ecId);
		roleDao.save(role);
		return role;

	}

	@Transactional(readOnly = false)
	public Long save(Long id, Long pId, String name, List<Long> aIds, Long ecId) {

		Role role = null;
		if (id < 1L) {
			role = new Role();
			Role parentRole = getRole(pId);
			role.setParentRole(parentRole);
			role.getRoles().addAll(parentRole.getRoles());
			role.getRoles().add(role);
		} else {
			role = getRole(id);
		}
		if (aIds.size() > 0) {
			Set<Action> actions = new HashSet<Action>();
			for (Long aId : aIds) {
				Action action = actionDao.findOne(aId);
				actions.add(action);
			}
			role.setAction(actions);
		}
		if (!"".equals(name))
			role.setRoleName(name);
		if (ecId > 0)
			role.setEc(ecDao.findOne(ecId));

		roleDao.save(role);

		return role.getId();
	}

	@Transactional(readOnly = false)
	public void save(Role role) {
		roleDao.save(role);
	}

	public Role findOne(Long id) {
		return roleDao.findOne(id);
	}

	public void setEcAndRoleRecoed(Role role, Long ecId) {
		Ec ec = ecDao.findOne(ecId);
		role.setEc(ec);
	}

	public List<Role> findListWithParams(String params, int pageNo, int pageSize, String permissions) {
		return roleDao.getListWithParams(params, pageNo, pageSize, permissions);
	}

	public long findCountWithParams(String params, String permissions) {
		return roleDao.getCountWithParams(params, permissions);
	}

	public Role getRole(Long id) {
		return roleDao.findOne(id);
	}

	public List<Role> getRoleByPId(Long pId) {
		return roleDao.getRoleByPId(pId);
	}

	@Transactional(readOnly = false)
	public Long saveRole(String params, Long[] permissions, Long ecId) {
		JSONObject roleDTO = SystemUtil.fromObject(params);
		Long id = roleDTO.getLong("id");
		String roleName = roleDTO.getString("name");

		Role role = null;
		if (id.equals(Long.valueOf(-1))) {
			role = new Role();

			Long pId = roleDTO.getLong("pId");
			Role parentRole = roleDao.findOne(pId);
			role.setParentRole(parentRole);

			// 企业信息
			Ec ec = ecDao.findOne(ecId);
			role.setEc(ec);

			for (Long rId : permissions) {
				Role rl = roleDao.findOne(rId);
				if (rl == null)
					continue;
				role.getRoles().add(rl);
			}
		} else {
			role = roleDao.findOne(id);
		}

		role.setRoleName(roleName);
		roleDao.save(role);
		role.getRoles().add(role);
		return role.getId();
	}

	public Role find(String roleName) {
		return roleDao.findByRoleName(roleName);

	}

	public List<Map<String, Object>> loadTree(String permission) {
		List<Role> list = roleDao.loadTree(permission);
		List<Map<String, Object>> voList = new ArrayList<Map<String, Object>>();
		for (Role ro : list) {
			Map<String, Object> roMap = new HashMap<String, Object>();
			roMap.put("name", ro.getRoleName());
			roMap.put("id", ro.getId());
			Role parentRo = ro.getParentRole();
			roMap.put("pId", parentRo == null ? null : parentRo.getId());
			voList.add(roMap);

		}
		return voList;
	}

	// 操作
	@Transactional(readOnly = false)
	public int grantRole(String ids, Long roleId) {
		Role role = getRole(roleId);
		if (role == null)
			return 2;
		role.getAction().clear();
		if (StringHelper.isNotEmpty(ids)) {
			String[] idarr = ids.split(",");
			for (int i = 0; i < idarr.length; i++) {
				if (StringHelper.isEmpty(idarr[i])) {
					continue;
				}
				Action action = actionDao.findOne(Long.valueOf(idarr[i]));
				if (action == null)
					continue;
				role.getAction().add(action);
			}
		}
		roleDao.save(role);
		return 1;
	}

	/**
	 * 加载
	 *
	 * @param roleIds
	 * @return
	 */
	public List<Map<String, Object>> loadTreeRoleUser(List<Long> roleIds) {
		List<Map<String, Object>> voList = new ArrayList<Map<String, Object>>();
		for (Long roleId : roleIds) {
			Role role = roleDao.findOne(roleId);
			Map<String, Object> roMap = new HashMap<String, Object>();
			roMap.put("name", role.getRoleName());
			roMap.put("id", TREE_ID_PREFIX + role.getId());
			Role parentRo = role.getParentRole();
			roMap.put("pId", parentRo == null ? null : TREE_ID_PREFIX
					+ parentRo.getId());
			roMap.put("nocheck", true);
			voList.add(roMap);
			Set<User> users = role.getUser();
			for (User user : users) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				userMap.put("name", user.getName());
				userMap.put("id", user.getId());
				userMap.put("pId", TREE_ID_PREFIX + role.getId());
				voList.add(userMap);
			}
		}
		return voList;
	}

	public List<Role> getAll() {
		return roleDao.findAll();
	}
}
