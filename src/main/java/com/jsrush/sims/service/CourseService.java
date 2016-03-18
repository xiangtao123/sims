package com.jsrush.sims.service;

import java.util.List;
import java.util.Map;

import com.jsrush.sims.entity.Course;

/**
 * 课程
 * @author sunburst
 *
 */
public interface CourseService {

	/**
	 * 查询课程列表
	 * 
	 * @param condtion
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> findPageList(Course condtion, Integer pageNo, Integer pageSize);
	
	/**
	 * 新增或更新
	 * 
	 * @param dto
	 */
	void saveOrUpdate(Course dto);
	
	/**
	 * 删除
	 * @param ids
	 */
	void delete(List<Long> ids);
	
	/**
	 * 查询院系列表
	 * @param ecId
	 * @return
	 */
	List<Map<Long, String>> findListByEcId(Long ecId);
		
}
