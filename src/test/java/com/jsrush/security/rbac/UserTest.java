package com.jsrush.security.rbac;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.jsrush.security.base.BaseTest;
import com.jsrush.security.rbac.entity.User;
import com.jsrush.security.rbac.service.RoleService;
import com.jsrush.security.rbac.service.UserService;

@TransactionConfiguration(defaultRollback=true)
public class UserTest extends BaseTest {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;
	
	
	@Test
	public void saveOrUpdateUser() {
		User user = userService.findUserByLoginName("admin");
		if (user == null) {
			user = new User();
			user.setLoginName("admin");
			user.setRegisterDate(new Date());
			user.setRole(roleService.findOne(1L));
		}
		user.setPlainPassword("111111");
		user.setName("柳海龙");
		userService.updateUser(user);
	}
}
