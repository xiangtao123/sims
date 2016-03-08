package com.jsrush.security.admin.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.jsrush.security.base.BaseTest;
import com.jsrush.security.rbac.service.ActionService;

@TransactionConfiguration(defaultRollback=false)
public class UserDataInitTest extends BaseTest {
	
	@Autowired
	private ActionService actionService;
	
	@Test
	public void testInitAdminUser(){
		
	}
}
