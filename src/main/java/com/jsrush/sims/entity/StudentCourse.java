package com.jsrush.sims.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jsrush.common.entity.IdEntity;

/**
 * 学生选课&成绩表
 * 
 * @author sunburst
 * 
 */
@Entity
@Table(name="t_student _course")
public class StudentCourse extends IdEntity {

	private static final long serialVersionUID = -5633441084408306990L;

	/**
	 * 学生
	 */
	private Student student;
	
	/**
	 * 课程
	 */
	private Course course;
	
	/**
	 * 成绩
	 */
	private String grade;
	
	/**
	 * 更新时间
	 */
	private Timestamp updateTime;

	/**
	 * 学校
	 */
	private Long ecId;
	
	@ManyToOne
	@JoinColumn(name="student_id")
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	@ManyToOne
	@JoinColumn(name="course_id")
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Column(name="update_time")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Long getEcId() {
		return ecId;
	}

	public void setEcId(Long ecId) {
		this.ecId = ecId;
	}
	
}
