package com.jsrush.security.rbac.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsrush.security.rbac.entity.Role;
import com.jsrush.security.rbac.entity.User;
import com.jsrush.security.rbac.service.EcService;
import com.jsrush.security.rbac.service.RoleService;
import com.jsrush.security.rbac.service.UserService;

/**
 * 用户注册
 * 
 * @author sunburst
 */
@RequestMapping(value="/register")
@Controller
public class UserRegisterController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserRegisterController.class);

	@Autowired
	private EcService ecService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 进入用户注册界面
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/index")
	public String index(HttpSession session, Model model) {
		LOGGER.info("进入用户注册界面");
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> ecList = (List<Map<String, Object>>) session.getAttribute("ec_list");
		if (CollectionUtils.isEmpty(ecList)) {
			ecList = ecService.findList();
			session.setAttribute("ec_list", ecList);
		}
		model.addAttribute("ecList", ecList);
		return "system/user_register";
	}
	
	/**
	 * 根据学校查询可注册的角色列表
	 * 
	 * @param ecId
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/findRoleListByEcId")
	@ResponseBody
	public List<Map<String, Object>> findRoleListByEcId(Long ecId, HttpSession session) {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> roleList= (List<Map<String, Object>> ) session.getAttribute("role_by_ec_" + ecId);
		if (CollectionUtils.isEmpty(roleList)) {
			roleList = roleService.findListByOpenRegister(ecId);
			session.setAttribute("role_by_ec_" + ecId, roleList);
		}
		return roleList;
	}
	
	/**
	 * 保存新注册用户
	 * 
	 * @param roleId
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/addUser")
	@ResponseBody
	public int addUser(Long roleId, User dto) {
		LOGGER.info("用户注册-开始保存");
		
		if (null == roleId || 1 > roleId) {
			return -1;
		}
		String loginName = dto.getLoginName();
		if (StringUtils.isBlank(loginName)) {
			return -1;
		}
		String plainPassword = dto.getPlainPassword();
		if (StringUtils.isBlank(loginName)) {
			return -1;
		}
		
		try {
			Role role = roleService.findOne(roleId);
			if (null == role || 1 != role.getOpenRegister()) {
				return -1;
			}
			
			User sameLoginNameUser = userService.findUserByLoginName(loginName);
			if (null != sameLoginNameUser) {
				return -3;
			}
			
			userService.registerUser(role, loginName, dto.getName(), plainPassword, dto.getEmail());
			
			LOGGER.info("用户注册-注册成功:" + loginName);
		} catch (Exception e) {
			LOGGER.error("用户注册-失败", e);
			return -2;
		}
		return 1;
	}
	
}
