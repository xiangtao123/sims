package com.jsrush.security.rbac.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.jsrush.security.rbac.entity.Role;

import java.util.List;

public interface RoleDao extends PagingAndSortingRepository<Role, Long>, RoleDaoCustom {

    Role findByRoleName(String roleName);

    List<Role> findAll();
}
