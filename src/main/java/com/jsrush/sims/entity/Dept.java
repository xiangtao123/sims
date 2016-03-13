package com.jsrush.sims.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jsrush.common.entity.IdEntity;

/**
 * 部门院系
 * 
 * @author sunburst
 */
@Entity
@Table(name="t_dept")
public class Dept extends IdEntity {

	private static final long serialVersionUID = -5316277676173860498L;

	/**
	 * 院系名称
	 */
	private String deptName;
	
	/**
	 * 类型
	 */
	private String type;

	/**
	 * 父级部门ID
	 */
	private Integer parentId;
	
	/**
	 * 企业（学校）ID
	 */
	private Long ecId;
	
	private String remark;

	
	@Column(name="dept_name")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name="ec_id")
	public Long getEcId() {
		return ecId;
	}

	public void setEcId(Long ecId) {
		this.ecId = ecId;
	}

	@Column(name="p_id")
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

}
