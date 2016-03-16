package com.jsrush.sims.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.alibaba.fastjson.JSONObject;
import com.jsrush.security.base.BaseTest;
import com.jsrush.sims.entity.Dept;
import com.jsrush.sims.service.DeptService;

/**
 * 部门院系
 * 
 * @author sunburst
 */
@TransactionConfiguration(defaultRollback=true)
public class DeptServiceTest extends BaseTest {

	@Autowired
	private DeptService deptService;
	
	@Test
	public void testFindPageList() {
		Dept condtion=new Dept();
		condtion.setEcId(1L);
		Integer pageNo=1;
		Integer pageSize=10;
		Map<String, Object> pageMap = deptService.findPageList(condtion, pageNo, pageSize);
		Assert.assertNotNull(pageMap);
		logger.info(JSONObject.toJSONString(pageMap));
	}
	
	@Test
	public void testSaveOrUpdate() {
		Dept dto = new Dept();
		dto.setDeptName("宇航学院");
		dto.setType("1");
		dto.setEcId(1L);
		dto.setRemark("强地、扬信、拓天");
		deptService.saveOrUpdate(dto);
	}
	
	@Test
	public void testDelete() {
		List<Long> ids = new ArrayList<Long>();
		ids.add(1L);
		deptService.delete(ids);
	}
	
	
}
