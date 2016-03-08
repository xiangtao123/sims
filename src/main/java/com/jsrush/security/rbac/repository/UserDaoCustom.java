package com.jsrush.security.rbac.repository;

import java.util.List;

import com.jsrush.security.rbac.entity.User;

public interface UserDaoCustom {
	
	List<User> getListWithParams(String params, int pageNo, int pageSize, String permissions);

	Long getCountWithParams(String params, String permissions);
	
	List<User> findListByLoginId(String loginIds);
	
	List<User> loadList(String permissions);
}
