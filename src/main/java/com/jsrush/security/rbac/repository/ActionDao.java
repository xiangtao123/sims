package com.jsrush.security.rbac.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jsrush.security.rbac.entity.Action;

import java.util.List;

public interface ActionDao extends PagingAndSortingRepository<Action, Long>,ActionDaoCustom{
    public Action findByActionName(String name);
    public Action findByActionText(String title);
    public List<Action> findByAction(Action action);
}
