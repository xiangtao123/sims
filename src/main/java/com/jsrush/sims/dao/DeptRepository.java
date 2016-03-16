package com.jsrush.sims.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsrush.sims.entity.Dept;

public interface DeptRepository extends JpaRepository<Dept, Long>, DeptRepositoryCustom {

}
