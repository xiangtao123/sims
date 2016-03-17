package com.jsrush.sims.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jsrush.common.entity.IdEntity;

/**
 * 专业
 * @author sunburst
 */
@Entity
@Table(name="t_speciality")
public class Speciality extends IdEntity {

	private static final long serialVersionUID = 8376749150988517831L;
	
	/**
	 * 专业名称
	 */
	private String name;
	
	/**
	 * 专业代码
	 */
	private String code;
	
	/**
	 * 修业年限
	 */
	private int length;
	
	/**
	 * 学位门类
	 */
	private String degreeType;
	
	/**
	 * 必修学分
	 */
	private int requireCredit;

	/**
	 * 选修学分
	 */
	private int optionCredit;

	/**
	 * 所属学院
	 */
	private Dept dept;

	/**
	 * 所属学校
	 */
	private Long ecId;
	
	/**
	 * 学院ID
	 */
	private Long deptId;
	
	@Transient
	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	@Column(name="degree_type")
	public String getDegreeType() {
		return degreeType;
	}

	public void setDegreeType(String degreeType) {
		this.degreeType = degreeType;
	}

	@Column(name="require_credit")
	public int getRequireCredit() {
		return requireCredit;
	}

	public void setRequireCredit(int requireCredit) {
		this.requireCredit = requireCredit;
	}

	@Column(name="option_credit")
	public int getOptionCredit() {
		return optionCredit;
	}

	public void setOptionCredit(int optionCredit) {
		this.optionCredit = optionCredit;
	}

	@ManyToOne
	@JoinColumn(name="dept_id")
	public Dept getDept() {
		return dept;
	}
	
	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@Column(name="ec_id")
	public Long getEcId() {
		return ecId;
	}

	public void setEcId(Long ecId) {
		this.ecId = ecId;
	}
	
}
