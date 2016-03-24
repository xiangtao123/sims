package com.jsrush.security.rbac.repository.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 企业查询
 * 
 * @author sunburst
 */
@Repository
public interface EcMapper {

	/**
	 * 企业列表
	 * 
	 * @param ecId
	 * @return
	 */
	List<Map<String, Object>> findList();
	
}
