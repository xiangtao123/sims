package com.jsrush.security.rbac;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.alibaba.fastjson.JSONObject;
import com.jsrush.security.base.BaseTest;
import com.jsrush.security.rbac.service.RoleService;

@TransactionConfiguration(defaultRollback=true)
public class RoleServiceTest extends BaseTest {

	@Autowired
	private RoleService roleService;
	
	@Test
	public void testFindListByOpenRegister() {
		Long ecId = 1L;
		List<Map<String, Object>> roleList = roleService.findListByOpenRegister(ecId);
		Assert.assertNotNull(roleList);
		logger.info(JSONObject.toJSONString(roleList));
	}
	
	
	
}
