package com.jsrush.sims.dao;

import java.util.List;
import java.util.Map;

import com.jsrush.sims.dto.StudentCourseCondition;
import com.jsrush.sims.entity.StudentCourse;

public interface StudentCourseRepositoryCustom {

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
	
}
