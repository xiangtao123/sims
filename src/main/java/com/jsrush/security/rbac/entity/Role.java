package com.jsrush.security.rbac.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.jsrush.common.entity.IdEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="t_jsrush_role")	
public class Role extends IdEntity {

	private String roleName;
	
	private Set<Action> action = new HashSet<Action>();
	
	private Ec ec;

	private Role parentRole;
	
	private Set<Role> childRole;
	
	private Set<User> user = new HashSet<User>();
	
	private Set<Role> roles = new HashSet<Role>();//管理权限的role collections.
	
	public String getRoleName() {
		return roleName;
	}
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name = "t_jsrush_role_action", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "action_id") })
	public Set<Action> getAction() {
		return action;
	}
	
	public void setAction(Set<Action> action) {
		this.action = action;
	}
	
	@JsonIgnore
	@OneToOne(fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
    @JoinColumn(name="ec_id")
	public Ec getEc() {
		return ec;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setEc(Ec ec) {
		this.ec = ec;
	}
	
	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "pid")
	public Role getParentRole() {
		return parentRole;
	}
	
	public void setParentRole(Role parentRole) {
		this.parentRole = parentRole;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "parentRole", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
	public Set<Role> getChildRole() {
		return childRole;
	}

	public void setChildRole(Set<Role> childRole) {
		this.childRole = childRole;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy="role")
	public Set<User> getUser() {
		return user;
	}

	public void setUser(Set<User> user) {
		this.user = user;
	}
	
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="t_jsrush_role_permissions", joinColumns = { @JoinColumn(name = "role_id") }, inverseJoinColumns = { @JoinColumn(name = "prole_id")})
	public Set<Role> getRoles() {
		return roles;
	}
	
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
	@Override
	public boolean equals(Object another) {
		if (another instanceof Role) {
			if (id == null)
				return false;
			else
				return id.equals(((Role) another).getId());
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
		str.append(getRoleName()).append("@").append(id);
		return str.toString();
	}

}
