package com.jsrush.sims.test;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.alibaba.fastjson.JSONObject;
import com.jsrush.security.base.BaseTest;
import com.jsrush.sims.dto.StudentCourseCondition;
import com.jsrush.sims.entity.StudentCourse;
import com.jsrush.sims.service.StudentCourseService;

/**
 * 选课
 * 
 * @author sunburst
 */
@TransactionConfiguration(defaultRollback=false)
public class StudentCourseServiceTest extends BaseTest {

	@Autowired
	private StudentCourseService studentCourseService;
	
	@Test
	public void testFindPageList() {
		StudentCourseCondition condition = new StudentCourseCondition();
		condition.setEcId(1L);
		
		Integer pageNo=1;
		Integer pageSize=10;
		Map<String, Object> list = studentCourseService.findPageList(condition, pageNo, pageSize);
		logger.info(JSONObject.toJSONString(list));
	}

	@Test
	public void testFindListByStudentIdAndCourseId() {
		Long studentId=1L;
		Long courseId=1L;
		
		List<StudentCourse> list = studentCourseService.findListByStudentIdAndCourseId(studentId, courseId);
		logger.info(JSONObject.toJSONString(list));
	}
	
	@Test
	public void testTakeCourse() {
		Long studentId = 1L;
		Long courseId = 1L;
		Long ecId = 1L;
		
		studentCourseService.takeCourse(studentId, courseId, ecId);
	}
	
	@Test
	public void testSaveGrade() {
		Long studentCourseId = 1L;
		String grade="20";
		studentCourseService.saveGrade(studentCourseId, grade);
	}
	
}
