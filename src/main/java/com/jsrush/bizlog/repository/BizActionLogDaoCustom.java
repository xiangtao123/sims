package com.jsrush.bizlog.repository;

import java.util.List;

import com.jsrush.bizlog.entity.BizActionLog;

public interface BizActionLogDaoCustom {

	List<String> findFieldByHql(String hql,Object[] params);
	
	List<Object> findFieldByHql(String hql);

	List<BizActionLog> getListWithParams(String params, int pageNo, int pageSize, String permissions);

	Long getCountWithParams(String params, String permissions);
	
}
