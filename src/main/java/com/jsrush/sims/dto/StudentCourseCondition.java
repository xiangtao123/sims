package com.jsrush.sims.dto;

import java.io.Serializable;

public class StudentCourseCondition implements Serializable {

	private static final long serialVersionUID = 8261636422973472624L;
	
	/**
	 * 学校ID
	 * 
	 */
	private Long ecId;
	
	/**
	 * 课程ID
	 */
	private Long courseId;
	
	/**
	 * 学号
	 */
	private String studentNo;
	
	/**
	 * 学生姓名
	 */
	private String studentName;
	
	/**
	 * 成绩
	 */
	private String grade;

	public Long getEcId() {
		return ecId;
	}

	public void setEcId(Long ecId) {
		this.ecId = ecId;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
}
