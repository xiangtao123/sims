package com.jsrush.sims.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.jsrush.common.entity.IdEntity;

/**
 * 课程
 * 
 * @author sunburst
 */
@Entity
@Table(name="t_course")
public class Course extends IdEntity {

	private static final long serialVersionUID = -1311020604680543036L;

	/**
	 * 课程名称
	 */
	private String name;
	
	/**
	 * 教材
	 */
	private String material;
	
	/**
	 * 学分
	 */
	private int credit;

	/**
	 * 所属专业
	 */
	private Set<Speciality> specialitys=new HashSet<Speciality>();
	
	/**
	 * 专业IDS
	 */
	private String specialityIds;
	
	/**
	 * 学校
	 */
	private Long ecId;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "t_speciality_course", joinColumns = { @JoinColumn(name = "course_id") }, inverseJoinColumns = { @JoinColumn(name = "speciality_id") })
	public Set<Speciality> getSpecialitys() {
		return specialitys;
	}
	
	public void setSpecialitys(Set<Speciality> specialitys) {
		this.specialitys = specialitys;
	}
	
	@Column(name="ec_id")
	public Long getEcId() {
		return ecId;
	}

	public void setEcId(Long ecId) {
		this.ecId = ecId;
	}

	@Transient
	public String getSpecialityIds() {
		return specialityIds;
	}
	
	public void setSpecialityIds(String specialityIds) {
		this.specialityIds = specialityIds;
	}
	
}
