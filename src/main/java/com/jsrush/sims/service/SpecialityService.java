package com.jsrush.sims.service;

import java.util.List;
import java.util.Map;

import com.jsrush.sims.entity.Speciality;

/**
 * 专业管理
 * @author sunburst
 *
 */
public interface SpecialityService {

	/**
	 * 查询专业管理列表
	 * 
	 * @param condtion
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> findPageList(Speciality condtion, Integer pageNo, Integer pageSize);
	
	/**
	 * 新增或更新
	 * 
	 * @param dto
	 */
	void saveOrUpdate(Speciality dto);
	
	/**
	 * 删除
	 * @param ids
	 */
	void delete(List<Long> ids);
	
	/**
	 * 查询专业列表
	 * @param ecId
	 * @return
	 */
	List<Map<Long, String>> findListByEcId(Long ecId);
	
}
