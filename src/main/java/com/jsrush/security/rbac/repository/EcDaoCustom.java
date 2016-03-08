package com.jsrush.security.rbac.repository;

import com.jsrush.security.rbac.entity.Ec;

import java.util.List;

public interface EcDaoCustom {


    List<Ec> findByCorpNameLike(String name, int index, int pageSize);

    int findCountByCorpNameLike(String name);
}
