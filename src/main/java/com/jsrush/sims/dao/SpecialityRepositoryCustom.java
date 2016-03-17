package com.jsrush.sims.dao;

import java.util.Map;

import com.jsrush.sims.entity.Speciality;

public interface SpecialityRepositoryCustom {

	/**
	 * 查询列表
	 * 
	 * @param condition
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> findPageList(Speciality condition, Integer pageNo, Integer pageSize);

}
