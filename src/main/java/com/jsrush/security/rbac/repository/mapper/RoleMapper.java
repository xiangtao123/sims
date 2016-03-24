package com.jsrush.security.rbac.repository.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 角色查询
 * 
 * @author sunburst
 */
@Repository
public interface RoleMapper {

	/**
	 * 根据企业查询开放注册的角色列表
	 * 
	 * @param ecId
	 * @return
	 */
	List<Map<String, Object>> findListByOpenRegister(Long ecId);
	
	
}
