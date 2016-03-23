package com.jsrush.sims.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jsrush.sims.entity.Student;

/**
 * 学生
 * @author sunburst
 *
 */
public interface StudentService {

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
	 * 注册、更新学生信息
	 * 
	 * @param dto
	 */
	void saveOrUpdate(Student dto);

	/**
	 * 教务人员审核学生信息
	 * 
	 * @param dto
	 * @param studentIds
	 */
	void updateStudent(Student dto, Set<Long> studentIds);

	/**
	 * 查询学生信息
	 * 
	 * @param studentId
	 * @return
	 */
	Student findOne(Long studentId);

	/**
	 * 查询学生信息，根据关联用户
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
