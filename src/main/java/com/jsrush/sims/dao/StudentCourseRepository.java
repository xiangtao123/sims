package com.jsrush.sims.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsrush.sims.entity.StudentCourse;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long>, StudentCourseRepositoryCustom {

}
