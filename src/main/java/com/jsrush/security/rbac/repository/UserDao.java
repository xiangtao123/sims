package com.jsrush.security.rbac.repository;

import com.jsrush.security.rbac.entity.Role;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.jsrush.security.rbac.entity.User;

import java.util.List;

public interface UserDao extends PagingAndSortingRepository<User, Long> ,UserDaoCustom{
	
	User findByLoginName(String loginName);

	List<User> findByRole(Role role);
}
