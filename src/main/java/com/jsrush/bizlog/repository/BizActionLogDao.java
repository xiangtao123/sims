package com.jsrush.bizlog.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jsrush.bizlog.entity.BizActionLog;

public interface BizActionLogDao extends PagingAndSortingRepository<BizActionLog, Long>,BizActionLogDaoCustom{

}
