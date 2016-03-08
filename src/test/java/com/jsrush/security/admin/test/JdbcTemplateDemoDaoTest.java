package com.jsrush.security.admin.test;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.jsrush.security.admin.dao.JdbcTemplateDemoDao;
import com.jsrush.security.base.BaseTest;

@TransactionConfiguration(defaultRollback=true)
public class JdbcTemplateDemoDaoTest extends BaseTest{

	@Autowired
	private JdbcTemplateDemoDao testDao;
	
	@Test
	public void addData(){
		try {
			testDao.addData("TD"+ (Math.random()));
		} catch (Exception e) {
			logger.error("新增失败："+e);
		}
	}
	
	@Test
	public void loadData(){
		List<Map<String,Object>> testDataList = testDao.loadTestData();
		Assert.assertNotNull(testDataList);
		logger.info(" testDataList is :"+JSONArray.fromObject(testDataList).toString());
	}
	
}
