package com.jsrush.security.rbac;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.jsrush.security.base.BaseTest;
import com.jsrush.security.rbac.entity.Role;
import com.jsrush.security.rbac.service.RoleService;

@TransactionConfiguration(defaultRollback=true)
public class RoleServiceTest extends BaseTest {

	@Autowired
	private RoleService roleService;
	
	@Test
	public void testAdd() {
		Role role = new Role();
		role.setRoleName("超级系统管理员");
		roleService.save(role);
	}
}
