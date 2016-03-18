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
import com.jsrush.sims.entity.Course;
import com.jsrush.sims.service.CourseService;

/**
 * 课程
 * 
 * @author sunburst
 */
@TransactionConfiguration(defaultRollback=true)
public class CourseServiceTest extends BaseTest {

	@Autowired
	private  CourseService courseService;
	
	@Test
	public void testFindList() {
		Long ecId=1L;
		List<Map<Long, String>> dataList = courseService.findListByEcId(ecId);
		logger.info(JSONObject.toJSONString(dataList));
	}
	
	@Test
	public void testFindPageList() {
		Course condtion=new Course();
		condtion.setEcId(1L);
		Integer pageNo=1;
		Integer pageSize=10;
		Map<String, Object> pageMap = courseService.findPageList(condtion, pageNo, pageSize);
		Assert.assertNotNull(pageMap);
		logger.info(JSONObject.toJSONString(pageMap));
	}
	
	@Test
	public void testSaveOrUpdate() {
		Course dto = new Course();
		dto.setName("计算机应用基础");
		dto.setCredit(4);
		dto.setMaterial("计算机应用基础（人民邮电出版社09年出版）：978-7-115-19899-0");
		dto.setEcId(1L);
		dto.setSpecialityIds("1,2");
		courseService.saveOrUpdate(dto);
	}
	
	@Test
	public void testDelete() {
		List<Long> ids = new ArrayList<Long>();
		ids.add(1L);
		courseService.delete(ids);
	}
	
}
