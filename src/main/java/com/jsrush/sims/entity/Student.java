package com.jsrush.sims.entity;

import java.sql.Timestamp;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jsrush.common.entity.IdEntity;

/**
 * 学生
 * 
 * @author sunburst
 */
@Entity
@Table(name = "t_student")
public class Student extends IdEntity {

	private static final long serialVersionUID = -1504268687631889697L;

	/**
	 * 学号
	 */
	private String studentNo;

	/**
	 * 学员姓名
	 */
	private String name;

	/**
	 * 性别
	 */
	private int gender;

	/**
	 * 生日
	 */
	private Date birthDay;

	/**
	 * 民族
	 */
	private String nation;

	/**
	 * 家庭住址
	 */
	private String homeAddr;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 身份证号
	 */
	private String idNo;

	/**
	 * 政治面貌
	 */
	private int politicalStatus;

	/**
	 * 入学日期
	 */
	private Date enrollDate;

	/**
	 * 毕业日期
	 */
	private Date graduationDate;

	/**
	 * 授予学位日期
	 */
	private Date degreeDate;

	/**
	 * 注册时间
	 */
	private Timestamp registerTime;

	/**
	 * 所学专业
	 */
	private Long specialityId;

	/**
	 * 系统用户ID
	 */
	private Long userId;

	/**
	 * 学校ID
	 */
	private Long ecId;
	
	/**
	 * 审核状态
	 */
	private int auditState;

	/**
	 * 审核时间
	 */
	private Timestamp auditTime;
	
	/**
	 * 审核意见
	 */
	private String auditRemark;
	
	
	
	@Column(name = "student_no")
	public String getStudentNo() {
		return studentNo;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo = studentNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	@Column(name = "birth_day")
	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	@Column(name = "home_addr")
	public String getHomeAddr() {
		return homeAddr;
	}

	public void setHomeAddr(String homeAddr) {
		this.homeAddr = homeAddr;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "id_no")
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	@Column(name = "political_status")
	public int getPoliticalStatus() {
		return politicalStatus;
	}

	public void setPoliticalStatus(int politicalStatus) {
		this.politicalStatus = politicalStatus;
	}

	@Column(name = "enroll_date")
	public Date getEnrollDate() {
		return enrollDate;
	}

	public void setEnrollDate(Date enrollDate) {
		this.enrollDate = enrollDate;
	}

	@Column(name = "graduation_date")
	public Date getGraduationDate() {
		return graduationDate;
	}

	public void setGraduationDate(Date graduationDate) {
		this.graduationDate = graduationDate;
	}

	@Column(name = "degree_date")
	public Date getDegreeDate() {
		return degreeDate;
	}

	public void setDegreeDate(Date degreeDate) {
		this.degreeDate = degreeDate;
	}

	@Column(name = "register_time")
	public Timestamp getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Timestamp registerTime) {
		this.registerTime = registerTime;
	}

	@Column(name = "speciality_id")
	public Long getSpecialityId() {
		return specialityId;
	}

	public void setSpecialityId(Long specialityId) {
		this.specialityId = specialityId;
	}

	@Column(name = "user_id")
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column(name = "ec_id")
	public Long getEcId() {
		return ecId;
	}

	public void setEcId(Long ecId) {
		this.ecId = ecId;
	}

	@Column(name = "audit_state")
	public int getAuditState() {
		return auditState;
	}

	public void setAuditState(int auditState) {
		this.auditState = auditState;
	}

	@Column(name = "audit_time")
	public Timestamp getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Timestamp auditTime) {
		this.auditTime = auditTime;
	}

	@Column(name = "audit_remark")
	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

}
