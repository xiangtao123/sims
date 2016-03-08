package com.jsrush.common.repository;

import java.util.List;

public interface ListPageIntl<T> {
	
	List<T> getListWithParams(String params, int pageNo, int pageSize, String permissions);

	Long getCountWithParams(String params, String permissions);
	
}
