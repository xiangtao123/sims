package com.jsrush.sims.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsrush.sims.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long>, CourseRepositoryCustom {

}
