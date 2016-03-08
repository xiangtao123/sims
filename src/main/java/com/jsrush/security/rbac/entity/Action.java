package com.jsrush.security.rbac.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.jsrush.common.entity.IdEntity;

@Entity
@Table(name = "t_jsrush_action")
public class Action extends IdEntity {

	private String actionName;			// 主键名称

	private Action action;				//	上级

	private String actionText;			//	显示的名字
	
	private String type;					// 资源类型：模块：module, 页面：page，元素：element
	
	private String targetUrl;			// 类型为页面时，对应的页面访问地址
	
	private String remark;				//	备注
	
	private Integer idx = 0;				// 顺序
	
	private Set<Role> roles = new HashSet<Role>();
	
	public String getActionName() {
		return actionName;
	}
	
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getActionText() {
		return actionText;
	}

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "pid")
	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public void setActionText(String actionText) {
		this.actionText = actionText;
	}
	
	@Override
	public boolean equals(Object another) {
		if (another instanceof Action) {
			if (id == null)
				return false;
			else
				return id.equals(((Action) another).getId());
		} else
			return false;
	}
	
	@Override
	public int hashCode() {
		return id == null ? super.hashCode() : id.hashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(getActionText()).append("@").append(id);
		return str.toString();
	}

	public String getType() {
		return type;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	@ManyToMany(mappedBy="action",fetch=FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH})
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	
}
