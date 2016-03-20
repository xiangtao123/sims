package com.jsrush.sims.service.impl;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsrush.sims.dao.StudentRepository;
import com.jsrush.sims.entity.Student;
import com.jsrush.sims.service.StudentService;


@Service("studentService")
@Transactional
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentRepository studentRepository;
	
	
	@Override
	public Map<String, Object> findPageList(Student condition, Integer pageNo, Integer pageSize) {
		Map<String, Object> pageMap = studentRepository.findPageList(condition, pageNo, pageSize);
		return pageMap;
	}

	
	@Override
	public void saveOrUpdate(Student dto) {
		Student entity = null;
		Long dtoId = dto.getId();
		if (dtoId != null && dtoId > 0) {
			entity = studentRepository.findOne(dtoId);
		}
		if (entity == null) {
			entity = new Student();
		}
		entity.setBirthDay(dto.getBirthDay());
		entity.setEcId(dto.getEcId());
		entity.setEmail(dto.getEmail());
		entity.setEnrollDate(dto.getEnrollDate());
		entity.setGender(dto.getGender());
		entity.setHomeAddr(dto.getHomeAddr());
		entity.setIdNo(dto.getIdNo());
		entity.setMobile(dto.getMobile());
		entity.setName(dto.getName());
		entity.setNation(dto.getNation());
		entity.setPoliticalStatus(dto.getPoliticalStatus());
		entity.setRegisterTime(dto.getRegisterTime());
		entity.setStudentNo(dto.getStudentNo());
		entity.setUserId(dto.getUserId());
		
		studentRepository.save(entity);
	}


	@Override
	public void updateStudent(Student dto, Set<Long> studentIds) {
		studentRepository.updateStudent(dto, studentIds);
	}


	@Override
	public Student findOne(Long studentId) {
		return studentRepository.findOne(studentId);
	}


	@Override
	public Student findByUserId(Long userId) {
		return studentRepository.findByUserId(userId);
	}
	
	
}
