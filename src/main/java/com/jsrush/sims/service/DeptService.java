package com.jsrush.sims.service;

import java.util.List;
import java.util.Map;

import com.jsrush.sims.entity.Dept;

/**
 * 部门院系
 * @author sunburst
 *
 */
public interface DeptService {

	/**
	 * 查询部门院系列表
	 * 
	 * @param condtion
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	Map<String, Object> findPageList(Dept condtion, Integer pageNo, Integer pageSize);
	
	/**
	 * 新增或更新
	 * 
	 * @param dto
	 */
	void saveOrUpdate(Dept dto);
	
	/**
	 * 删除
	 * @param ids
	 */
	void delete(List<Long> ids);
	
}
