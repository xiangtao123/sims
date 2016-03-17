package com.jsrush.sims.dao;

import java.util.List;
import java.util.Map;

import com.jsrush.sims.entity.Dept;

public interface DeptRepositoryCustom {

	/**
	 * 查询列表
	 * 
	 * @param condition
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> findPageList(Dept condition, Integer pageNo, Integer pageSize);
	
	/**
	 * 查询院系列表
	 * @param ecId
	 * @return
	 */
	List<Map<Long, String>> findListByEcId(Long ecId);
	
}
