package com.jsrush.sims.service;

import java.util.List;
import java.util.Map;

import com.jsrush.sims.dto.StudentCourseCondition;
import com.jsrush.sims.entity.StudentCourse;


public interface StudentCourseService {

	/**
	 * 查询列表
	 * 
	 * @param condition
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> findPageList(StudentCourseCondition condition, Integer pageNo, Integer pageSize);
	
	/**
	 * 根据学生和课程查询
	 * 
	 * @param studentId
	 * @param courseId
	 * @return
	 */
	List<StudentCourse> findListByStudentIdAndCourseId(Long studentId, Long courseId);

	/**
	 * 选课
	 * 
	 * @param studentId
	 * @param courseId
	 */
	void takeCourse(Long studentId, Long courseId, Long ecId);
	
	
	/**
	 * 录入成绩
	 * 
	 * @param studentCourseId
	 * @param gade
	 */
	void saveGrade(Long studentCourseId, String grade);
	
}
