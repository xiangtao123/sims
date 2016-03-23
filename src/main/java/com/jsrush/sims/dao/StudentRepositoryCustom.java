
package com.jsrush.sims.dao;

import java.util.List;
import java.util.Map;

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
	 * 查询学生信息根据关联的用户
	 * 
	 * @param userId
	 * @return
	 */
	Student findByUserId(Long userId);
	
	/**
	 * 查询学生列表
	 * 
	 * @param ecId
	 * @return
	 */
	List<Map<String, Object>> findListByEcId(Long ecId);
	
}
