package com.jsrush.security.rbac.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsrush.security.rbac.entity.User;
import com.jsrush.security.rbac.service.UserService;

/**
 * LoginController负责打开登录页面(GET请求)和登录出错页面(POST请求)，
 * 
 * 真正登录的POST请求由Filter完成,
 * 
 * @author  sunburst
 */
@Controller
@RequestMapping(value = "/login")
public class LoginController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String login() {
		
		return "login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail() {

		return "login";
	}

	@RequestMapping(value = "/kaptcha")
	@ResponseBody
	public boolean kaptcha(@RequestParam(value = "captcha") String captcha,
			HttpServletRequest request) {
		String kaptchaExpected = (String) request.getSession().getAttribute(
				com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
		if (captcha == null || !captcha.equalsIgnoreCase(kaptchaExpected)) { // 判断内容是否相同
			return false;
		} else {
			return true;
		}
	}

	@RequestMapping(value = "/checkLoginName")
	@ResponseBody
	public boolean checkLoginName(@RequestParam("username") String username) {
		User user = userService.findUserByLoginName(username);
		if (user == null) {
			return false;
		} else {
			return true;
		}

	}

	@RequestMapping(value = "/checkPwd")
	@ResponseBody
	public boolean checkPwd(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		if (username == null) {
			return false;
		} else {
			User user = userService.findUserByLoginName(username);
			if (user == null){
				return false;
			}
			String enOldPwd = userService.entryptPassword(password, user.getSalt()); // 加密前台接收的密码
			String dbPwd = user.getPassword(); // 数据库中存的密码
			boolean isEq = enOldPwd.equals(dbPwd);
			if (!isEq) {
				return false;
			} else {
				return true;
			}
		}
	}

}
