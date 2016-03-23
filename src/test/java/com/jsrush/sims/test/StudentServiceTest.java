package com.jsrush.sims.test;

import java.sql.Date;
import java.text.ParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.alibaba.fastjson.JSONObject;
import com.jsrush.security.base.BaseTest;
import com.jsrush.sims.entity.Student;
import com.jsrush.sims.service.StudentService;
import com.jsrush.util.DateUtil;

/**
 * 学生管理系统
 * 
 * @author sunburst
 */
@TransactionConfiguration(defaultRollback=true)
public class StudentServiceTest extends BaseTest {

	@Autowired
	private StudentService studentService;
	
	@Test
	public void testFindListByEcId() {
		Long ecId = 1L;
		List<Map<String, Object>> list = studentService.findListByEcId(ecId);
		Assert.assertNotNull(list);
		logger.info(JSONObject.toJSONString(list));
	}
	
	@Test
	public void testFindPageList() {
		Student condtion = new Student();
		condtion.setEcId(1L);
		Integer pageNo=1;
		Integer pageSize=10;
		Map<String, Object> pageMap = studentService.findPageList(condtion, pageNo, pageSize);
		Assert.assertNotNull(pageMap);
		logger.info(JSONObject.toJSONString(pageMap));
	}
	
	@Test
	public void testSaveOrUpdate() {
		Date sqlDate=null;
		try {
			sqlDate = DateUtil.parseSQLDate("1990-08-01");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Student dto = new Student();
		dto.setBirthDay(sqlDate);
		dto.setEcId(1L);
		dto.setEmail("zhangke@163.com");
		dto.setMobile("13600000012");
		dto.setGender(1);
		dto.setHomeAddr("天津市和平区");
		dto.setIdNo("371481119011086542");
		dto.setName("张可");
		dto.setNation("汉族");
		dto.setPoliticalStatus(1);
		dto.setRegisterTime(DateUtil.getNowTimestamp());
		dto.setSpecialityId(1L);
		dto.setStudentNo("101020160610001");
		dto.setEcId(1L);
		
		studentService.saveOrUpdate(dto);
	}
	
	@Test
	public void testUpdateStudent() {
		Student dto = new Student();
		dto.setAuditState(1);
		dto.setAuditRemark("通过");
		dto.setAuditTime(DateUtil.getNowTimestamp());
		
		Set<Long> studentIds = new HashSet<Long>();
		studentIds.add(1L);
		studentService.updateStudent(dto, studentIds);
	}
	
	@Test
	public void testFindOne() {
		Long studentId=1L;
		Student student = studentService.findOne(studentId);
		Assert.assertNotNull(student);
		logger.info(JSONObject.toJSONString(student));
	}
	
	@Test
	public void testFindByUserId() {
		Long userId=-1L;
		Student student = studentService.findByUserId(userId);
		Assert.assertNotNull(student);
		logger.info(JSONObject.toJSONString(student));
	}
}
