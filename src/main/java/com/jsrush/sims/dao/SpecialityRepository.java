package com.jsrush.sims.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsrush.sims.entity.Speciality;

public interface SpecialityRepository extends JpaRepository<Speciality, Long>, SpecialityRepositoryCustom {

}
