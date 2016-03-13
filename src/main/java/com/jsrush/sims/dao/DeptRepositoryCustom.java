package com.jsrush.sims.dao;

import java.util.List;

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
	List<Dept> findPageList(Dept condition, Integer pageNo, Integer pageSize);
	
}
