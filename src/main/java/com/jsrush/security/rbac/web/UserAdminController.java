package com.jsrush.security.rbac.web;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.jsrush.bizlog.annotation.ActionLog;
import com.jsrush.bizlog.annotation.ConditionColumn;
import com.jsrush.bizlog.annotation.WithArgment;
import com.jsrush.bizlog.annotation.WithArgments;
import com.jsrush.bizlog.annotation.WithColumn;
import com.jsrush.bizlog.annotation.WithTable;
import com.jsrush.security.rbac.entity.User;
import com.jsrush.security.rbac.service.RoleService;
import com.jsrush.security.rbac.service.ShiroManager;
import com.jsrush.security.rbac.service.UserService;
import com.jsrush.util.LogUtil;
import com.jsrush.util.StringHelper;
import com.jsrush.util.SystemUtil;

/**
 * 管理员管理用户的Controller.
 * 
 * @author sunburst
 */
@Controller
@RequestMapping(value = "/usermanagement")
public class UserAdminController {

	private static Logger logger = LoggerFactory.getLogger(UserAdminController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private ShiroManager shiroManager;
	
	@RequestMapping(method = RequestMethod.GET)
	@RequiresPermissions("user:open")
	public String index(Model model) {
		return "usermanagement/usermanagement";
	}
	
	@RequestMapping(value="/list")
	@ResponseBody
	public Map<String,Object> list(@RequestParam(required = false, value = "params") String params,
					   @RequestParam(defaultValue = "1", value = "page") int pageNo, 
					   @RequestParam(defaultValue = "10", value = "rows") int pageSize){
		try {
			String permissions = shiroManager.getCurrentRoleIdStr();
			logger.info("permissions:" + permissions);
			List<User> list = userService.findListWithParams(params, pageNo, pageSize,permissions);
			return SystemUtil.createPageInfoMap(pageNo, pageSize, userService.findCountWithParams(params,permissions ), list);
		} catch (Exception e) {
			e.printStackTrace();
			return SystemUtil.createPageInfoMap(pageNo, pageSize, 0, null);
		}
	}
	
	@ActionLog(actionType="删除",bizType="用户管理",expectedExp="returnValue == 1")
	@WithTable(name="User",withColumns={@WithColumn(text="登陆名",name="loginName"), @WithColumn(text="用户名",name="name"), @WithColumn(text="角色名称",name="role.roleName")},conditionColumns={@ConditionColumn(argIndex=0,name="id", operator=ConditionColumn.INLIST)})
	@RequestMapping(value="/delete")
	@ResponseBody
	@RequiresPermissions("user:btn:remove")
	public int delete(@RequestParam(value = "params") String params){
		 if (StringHelper.isEmpty(params))
			 return 0;
		 try {
			 userService.deleteUser(params);
			 return 1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	@RequestMapping(value="/checkinput")
	@ResponseBody
	public String check(@RequestParam("loginName") String loginName){
		
		if(userService.findUserByLoginName(loginName)==null){
			return "true";
		}
		else {
			
			return "false";
		}
	}

	@RequestMapping(value="/changeinput")
	@ResponseBody
	public String changecheck(	@RequestParam("edit_id") Long id,
								@RequestParam("oldPassword") String oldPassword)
	{
		User newUser=userService.getUser(id);
		String enOldPwd = userService.entryptPassword(oldPassword, newUser.getSalt()); //加密前台接收的密码
		String dbPwd = newUser.getPassword(); //数据库中存的密码
		boolean isEq = enOldPwd.equals(dbPwd);
		if (!isEq){
			return "false";
		}
		else {
			return "true";
		}
	}

	@ActionLog(actionType="新增",bizType="用户管理",expectedExp="returnValue == 1")
	@WithArgments(withArgments={@WithArgment(text="登陆名",argExpress="loginName"), @WithArgment(text="用户名",argExpress="name"), @WithArgment(text="角色名称",argExpress="addRoleName") })
	@RequestMapping(value="/save")
	@ResponseBody
	@RequiresPermissions("user:btn:add")
	public int save(@RequestParam("params") String params){
		 try {
			 Set<Long> aboveRoleId = shiroManager.getCurrentAboveRoleId();
			 logger.info("aboveRoleId:" + aboveRoleId);
			 return userService.saveUser(params);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
			return 0;
		}
	}
	
	@ActionLog(actionType="修改密码",bizType="用户管理",expectedExp="returnValue == 1")
	@WithTable(name="User",withColumns={@WithColumn(text="登陆名",name="loginName"), @WithColumn(text="用户名",name="name"), @WithColumn(text="角色名称",name="role.roleName")},conditionColumns={@ConditionColumn(argIndex=0, name="id", argExpress="id" ,argType = ConditionColumn.JSONSTR)})
	@RequestMapping(value="/changePwd")
	@ResponseBody
	@RequiresPermissions("user:btn:resetpwd")
	public int changeP(@RequestParam("params") String params){
		 try {
			 return userService.changePwd(params);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	@ActionLog(actionType="修改角色",bizType="用户管理",expectedExp="returnValue == 1")
	@WithTable(name="User",withColumns={@WithColumn(text="登陆名",name="loginName"), @WithColumn(text="用户名",name="name"), @WithColumn(text="角色名称",name="role.roleName")},conditionColumns={@ConditionColumn(argIndex=0, name="id", argExpress="id" ,argType = ConditionColumn.JSONSTR)})
	@WithArgments(withArgments={@WithArgment(text=" 更新后的内容为： 用户名",argExpress="name"), @WithArgment(text="角色名称",argExpress="editRoleName") })
	@RequestMapping(value="/change")
	@ResponseBody
	@RequiresPermissions("user:btn:resetrole")
	public int change(@RequestParam(value="params")String params){
		 
		 logger.info("edit user: " + params );
		 try {
			 return userService.updateUser(params);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0;
		}
	}
	
	/**
	 * 所有RequestMapping方法调用前的Model准备方法, 实现Struts2 Preparable二次部分绑定的效果,先根据form的id从数据库查出User对象,再把Form提交的内容绑定到该对象上。
	 * 因为仅update()方法的form中有id属性，因此仅在update时实际执行.
	 */
	@ModelAttribute
	public void getUser(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("user", userService.getUser(id));
		}
	}


	@RequestMapping(value="/loadList")
	@ResponseBody
	public List<Map<String, Object>> loadList(){
		List<Map<String, Object>> voList = new ArrayList<Map<String,Object>>();
		try {
			String permissions = shiroManager.getCurrentRoleIdStr();
			logger.info("permissions:" + permissions);
			List<User> list = userService.loadList(permissions);
			for (User u : list) {
				Map<String, Object> voMap = new HashMap<String, Object>();
				voMap.put("value", u.getLoginName());
				voMap.put("text", u.getName());
				voList.add(voMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(LogUtil.stackTraceToString(e));
		}
		return voList;
	}
	
}
