package com.jsrush.security.rbac.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jsrush.common.entity.IdEntity;

/**
 * 企业
 * 
 * @author sunburst
 *
 */
@Entity
@Table(name="t_jsrush_ec")
public class Ec extends IdEntity {
	
	private static final long serialVersionUID = -6888999836373446181L;

	private String corpName;//集团客户名称

	private String corpAccount;//集团客户帐号
	
	private String linkMan;//集团联系人

	private String phoneNum;//集团联系人号码

	private Integer actionState;//集团受理标志 1 : 订购 2 : 暂停 3 : 恢复 4 : 变更 5 : 退订

	private Set<Role> roles = new HashSet<Role>();
	
	private Timestamp createTime;
	
	private Timestamp updateTime;
	
	@Column(name="corp_name")
	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	@Column(name="corp_account")
	public String getCorpAccount() {
		return corpAccount;
	}

	public void setCorpAccount(String corpAccount) {
		this.corpAccount = corpAccount;
	}

	@Column(name="link_man")
	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	@Column(name="phone_num")
	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	@Column(name="action_state")
	public Integer getActionState() {
		return actionState;
	}

	public void setActionState(Integer actionState) {
		this.actionState = actionState;
	}

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "t_jsrush_ec_permissions", joinColumns = { @JoinColumn(name = "ec_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Column(name="create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Column(name="update_time")
	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public int hashCode() {
		return id == null ? super.hashCode() : id.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Ec) {
			Ec otherObj = (Ec) obj;
			if (id != null && otherObj.getId() != null && otherObj.getId().longValue() == id) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return id == null ? super.toString() : id+"@"+getCorpName();
	}
}
