package com.jsrush.security.rbac.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsrush.security.rbac.entity.Action;
import com.jsrush.security.rbac.entity.Role;
import com.jsrush.security.rbac.service.ActionService;
import com.jsrush.security.rbac.service.RoleService;
import com.jsrush.security.rbac.service.ShiroManager;
import com.jsrush.security.rbac.service.UserService;
import com.jsrush.util.StringHelper;
import com.jsrush.util.SystemUtil;

/**
 * 管理员管理用户的Controller.
 * 
 */
@Controller
@RequestMapping(value = "/rolemanagement")
public class RoleManagementController {

	private Logger log = LoggerFactory.getLogger(RoleManagementController.class);

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@Autowired
	private ActionService actionService;

	@Autowired
	private ShiroManager shiroManager;

	@RequestMapping(method = RequestMethod.GET)
	@RequiresPermissions("role:open")
	public String index(Model model) {
		return "usermanagement/rolemanagement";
	}

	@RequestMapping(value = "/list")
	@ResponseBody
	public Map<String, ?> list(
			@RequestParam(required = false, value = "params") String params,
			@RequestParam(defaultValue = "1", value = "page") int pageNo,
			@RequestParam(defaultValue = "10", value = "rows") int pageSize) {
		String permissions = shiroManager.getCurrentRoleIdStr();
		log.info("permissions:" + permissions);
		List<Role> list = roleService.findListWithParams(params, pageNo,pageSize, permissions);
		try {
			return SystemUtil.createPageInfoMap(pageNo, pageSize,roleService.findCountWithParams(params, permissions), list);
		} catch (Exception e) {
			e.printStackTrace();
			return SystemUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}

	@RequestMapping(value = "/save")
	@ResponseBody
	public Long save(@RequestParam(value = "params") String params) {
		if (StringHelper.isEmpty(params))
			return 0l;
		log.info("save or edit role info : " + params);
		try {
			Set<Long> aboveRoleId = shiroManager.getCurrentAboveRoleId();
			Long[] permissions = aboveRoleId.toArray(new Long[aboveRoleId.size()]);
			Long ecId = shiroManager.getCurrentUserEcId();
			return roleService.saveRole(params, permissions, ecId);
		} catch (Exception e) {
			log.info(e.getMessage());
			e.printStackTrace();
			return 0l;
		}
	}

	@RequestMapping(value = "/loadtree")
	@ResponseBody
	public List<Map<String, Object>> loadTree() {
		try {
			String permissions = shiroManager.getCurrentRoleIdStr();
			log.info("permissions:" + permissions);
			return roleService.loadTree(permissions);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
			return new ArrayList<Map<String, Object>>();
		}
	}

	@RequestMapping(value = "/searchaction")
	@ResponseBody
	public List<Long> searchac(@RequestParam(value = "id") Long id) {
		List<Long> acList = new ArrayList<Long>();
		Role role = roleService.getRole(id);
		if (role == null)
			return new ArrayList<Long>();
		Set<Action> actions = role.getAction();
		for (Action action : actions) {
			acList.add(action.getId());
		}
		return acList;
	}

	@RequestMapping(value = "/grantRole")
	@ResponseBody
	public int grantRole(@RequestParam(value = "acIds") String acIds,
			@RequestParam(value = "roleId") Long roleId) {
		try {
			return roleService.grantRole(acIds, roleId);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return 0;
		}

	}

	@RequestMapping(value = "/delRole")
	@ResponseBody
	public int delRole(@RequestParam(value = "id") Long id) {
		try {
			return userService.deleteRole(id);
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return 2;
		}
	}

	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2
	 * Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getRole(
			@RequestParam(value = "id", defaultValue = "-1") Long id,
			Model model) {
		if (id != -1) {
			model.addAttribute("role", roleService.getRole(id));
		}
	}
	
	
	@RequestMapping(value = "/loadTreeRoleUser")
	@ResponseBody
	public List<Map<String, Object>> loadTreeRoleUser() {
		try {
			 Long[] currentLowerRoleIds = shiroManager.getCurrentLowerRoleIdsArray();
			 List<Long> roleIds = new ArrayList<Long>();
			 for (Long roleId : currentLowerRoleIds)
				 roleIds.add(roleId);
			return roleService.loadTreeRoleUser(roleIds );
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
			return new ArrayList<Map<String,Object>>();
		}
	}
	
}
