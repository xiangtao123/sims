package com.jsrush.sims.service.impl;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jsrush.sims.dao.StudentCourseRepository;
import com.jsrush.sims.dto.StudentCourseCondition;
import com.jsrush.sims.entity.Course;
import com.jsrush.sims.entity.Student;
import com.jsrush.sims.entity.StudentCourse;
import com.jsrush.sims.service.StudentCourseService;
import com.jsrush.util.DateUtil;

@Service("studentCourseService")
@Transactional(readOnly=true)
public class StudentCourseServiceImpl implements StudentCourseService {

	@Autowired
	private StudentCourseRepository studentCourseRepository;
	
	@Override
	public Map<String, Object> findPageList(StudentCourseCondition condition, Integer pageNo, Integer pageSize) {
		return studentCourseRepository.findPageList(condition, pageNo, pageSize);
	}

	@Override
	public List<StudentCourse> findListByStudentIdAndCourseId(Long studentId, Long courseId) {
		return studentCourseRepository.findListByStudentIdAndCourseId(studentId, courseId);
	}

	@Override
	public void takeCourse(Long studentId, Long courseId, Long ecId) {
		List<StudentCourse> listByStudentIdAndCourseId = findListByStudentIdAndCourseId(studentId, courseId);
		if (CollectionUtils.isNotEmpty(listByStudentIdAndCourseId)) {
			return;
		}
		Timestamp nowTimestamp = DateUtil.getNowTimestamp();
		
		Student student=new Student();
		student.setId(studentId);
		
		Course course=new Course();
		course.setId(courseId);
		
		StudentCourse entity = new StudentCourse();
		entity.setUpdateTime(nowTimestamp);
		entity.setEcId(ecId);
		entity.setCourse(course);
		entity.setStudent(student);
		studentCourseRepository.save(entity);
	}

	@Override
	public void saveGrade(Long studentCourseId, String grade) {
		Timestamp nowTimestamp = DateUtil.getNowTimestamp();
		StudentCourse studentCourse = studentCourseRepository.findOne(studentCourseId);
		if (studentCourse != null) {
			studentCourse.setGrade(grade);
			studentCourse.setUpdateTime(nowTimestamp);
			studentCourseRepository.save(studentCourse);
		}
	}

}
