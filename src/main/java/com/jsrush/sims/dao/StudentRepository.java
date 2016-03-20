package com.jsrush.sims.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsrush.sims.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long>, StudentRepositoryCustom {

}
