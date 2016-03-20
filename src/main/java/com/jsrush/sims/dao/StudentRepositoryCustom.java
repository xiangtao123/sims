
package com.jsrush.sims.dao;

import java.util.Map;
import java.util.Set;

import com.jsrush.sims.entity.Student;

public interface StudentRepositoryCustom {

	/**
	 * 查询列表
	 * 
	 * @param condition
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> findPageList(Student condition, Integer pageNo, Integer pageSize);
	
	/**
	 * 教务人员审核学生信息
	 * 
	 * @param dto
	 * @param studentIds
	 */
	void updateStudent(Student dto, Set<Long> studentIds);
	
	/**
	 * 查询学生信息根据关联的用户
	 * 
	 * @param userId
	 * @return
	 */
	Student findByUserId(Long userId);
	
}
