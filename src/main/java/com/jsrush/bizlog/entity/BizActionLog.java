package com.jsrush.bizlog.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.jsrush.bizlog.constants.BizLogActionType;
import com.jsrush.common.entity.IdEntity;
import com.jsrush.security.rbac.entity.Role;
import com.jsrush.util.TimestampJsonSerializer;

@Entity
@Table(name = "t_jsrush_bizaction_log")
public class BizActionLog extends IdEntity{

	private static final long serialVersionUID = -7935195165150465446L;

	private String actionType = BizLogActionType.TEMPSTR;	//	操作类型
	
	private String bizType;								//	业务类型
	
	private String bizContent;							//	业务信息
	
	private String bizInfo;								//	业务元信息
	
	private Long creatorId;								//	操作者ID: user.id
	
	private String creatorName;							//	操作者名称: user.name
	
	private Timestamp createTime;						//	创建时间

	private Set<Role> roles = new HashSet<Role>();		//	管理权限的role collections.
	
	@Column(name="action_type")
	public String getActionType() {
		return actionType;
	}

	@Column(name="biz_type")
	public String getBizType() {
		return bizType;
	}

	@Column(name="biz_content")
	public String getBizContent() {
		return bizContent;
	}

	@JsonIgnore
	@Column(name="biz_info",length=512)
	public String getBizInfo() {
		return bizInfo;
	}
	
	@Column(name="creator_id")
	@JsonIgnore
	public Long getCreatorId() {
		return creatorId;
	}

	@Column(name="creator_name")
	public String getCreatorName() {
		return creatorName;
	}

	@JsonSerialize(using=TimestampJsonSerializer.class)
	@Column(name="create_time")
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	public void setBizContent(String bizContent) {
		this.bizContent = bizContent;
	}

	public void setBizInfo(String bizInfo) {
		this.bizInfo = bizInfo;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="t_jsrush_bizaction_log_permissions", joinColumns = { @JoinColumn(name = "log_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id")})
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
