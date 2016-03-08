package com.jsrush.security.rbac;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.jsrush.exception.BizException;
import com.jsrush.security.base.BaseTest;
import com.jsrush.security.rbac.entity.Action;
import com.jsrush.security.rbac.entity.Role;
import com.jsrush.security.rbac.entity.User;
import com.jsrush.security.rbac.repository.ActionDao;
import com.jsrush.security.rbac.repository.RoleDao;
import com.jsrush.security.rbac.service.ActionService;
import com.jsrush.security.rbac.service.RoleService;
import com.jsrush.security.rbac.service.UserService;
import com.jsrush.security.rbac.vo.ActionDTO;
import com.jsrush.util.FileUtil;

/**
 * 初始化 角色/权限/用户
 * @author sunburst
 *
 */
@TransactionConfiguration(defaultRollback=true)
public class InitRootUserTest extends BaseTest {

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ActionService actionService;
	
	@Autowired
	private ActionDao actionDao;

	/**
	 * 1. 新增角色：超级系统管理员
	 */
	@Test
	public void testAddRootRole() {
		Role role = new Role();
		role.setRoleName("超级系统管理员");
		roleService.save(role);
		
		Assert.assertNotNull(role.getId());
		
	}

	/**
	 * 2. 新增用户：admin
	 */
	@Test
	public void testAddRootUser(){
		User user = userService.findUserByLoginName("admin");
		if (user == null) {
			user = new User();
			user.setLoginName("admin");
			user.setRegisterDate(new Date());
			
			Role rootRole = roleDao.getNullpId();
			Assert.assertNotNull(rootRole);
			user.setRole(rootRole);
		}
		user.setPlainPassword("111111");
		user.setName("柳海龙");
		userService.updateUser(user);
		
		Assert.assertNotNull(user.getId());
	}
	
	/**
	 * 3. 新增系统管理菜单：并授权给超级系统管理员
	 */
	@Test
	public void testAddRootMenu() {

		String dataFilePath = "/src/test/resources/datafile/menu_data.json";
		String dataStr = FileUtil.readFileText(dataFilePath);
		logger.info("dataStr is :\n"+dataStr);
		Assert.assertNotEquals("", dataStr);
		
		List<Long> roleIds = new ArrayList<Long>();
		Role rootRole = roleDao.getNullpId();
		Assert.assertNotNull(rootRole);
		
		roleIds.add(rootRole.getId());
		
		Long rootActionId = null;
		List<Action> rootActions = actionDao.findRootAction();
		if (CollectionUtils.isEmpty(rootActions)) {
			ActionDTO rootDTO = new ActionDTO();
			rootDTO.setKey("page:welcome");
			rootDTO.setName("登录");
			rootDTO.setpId(null);
			rootDTO.setTitle("登录系统");
			rootDTO.setType("gateway");
			try {
				rootActionId = actionService.saveOrUpdate(rootDTO, roleIds);
			} catch (BizException e) {
				e.printStackTrace();
			}
		}
		
		HashMap<Long, Long> idsMap = new HashMap<Long, Long>();
		JSONArray jsonArray = JSONArray.fromObject(dataStr);
		for (int i=0, len=jsonArray.size(); i < len; i++) {
			JSONObject jsonObj = jsonArray.getJSONObject(i);
			logger.info("convert to dto is : \n"+jsonObj.toString());
			ActionDTO dto = (ActionDTO) JSONObject.toBean(jsonObj, ActionDTO.class);
			
			Long dtoId = dto.getId();
			Long dtopId = dto.getpId();
			dto.setId(null);
			if (dtopId.longValue() == 0) {
				dto.setpId(rootActionId);
			} else {
				if (idsMap.containsKey(dtopId)) {
					dto.setpId(idsMap.get(dtopId));
				}
			}
			logger.info("save dtos is : \n"+JSONArray.fromObject(dto).toString());
			try {
				Long entityId = actionService.saveOrUpdate(dto, roleIds);
				idsMap.put(dtoId, entityId);
			} catch (BizException e) {
				e.printStackTrace();
			}
		}
	}
	
}
