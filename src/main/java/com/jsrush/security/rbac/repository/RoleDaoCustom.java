package com.jsrush.security.rbac.repository;

import java.util.List;

import com.jsrush.security.rbac.entity.Role;

public interface RoleDaoCustom {
	
	List<Role> getListWithParams(String params, int pageNo, int pageSize, String permissions);

	Long getCountWithParams(String params, String permissions);
	
	List<Role> loadTree(String permission);
	
	List<Role> getRoleByPId(Long pId);
	
	Role getNullpId();

}
