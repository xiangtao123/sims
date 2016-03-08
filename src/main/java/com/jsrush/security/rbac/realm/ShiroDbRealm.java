/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.jsrush.security.rbac.realm;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.jsrush.security.rbac.entity.Action;
import com.jsrush.security.rbac.entity.Ec;
import com.jsrush.security.rbac.entity.Role;
import com.jsrush.security.rbac.entity.User;
import com.jsrush.security.rbac.service.ShiroManager;
import com.jsrush.security.rbac.service.UserService;
import com.jsrush.util.Encodes;

public class ShiroDbRealm extends AuthorizingRealm {

	protected ShiroManager shiroManager;

	public ShiroManager getShiroManager() {
		return shiroManager;
	}
	
	public void setShiroManager(ShiroManager shiroManager) {
		this.shiroManager = shiroManager;
	}
	
	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		User user = shiroManager.findUserByLoginName(token.getUsername());
		if (user != null) {
			byte[] salt = Encodes.decodeHex(user.getSalt());
			Role role = user.getRole();
			String roleName = role == null ? "" : "-" +  role.getRoleName();
			return new SimpleAuthenticationInfo(new ShiroUser(user.getId(), user.getLoginName(), user.getName()+roleName),
					user.getPassword(), ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		User user = shiroManager.findUserByLoginName(shiroUser.loginName);
		
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Role role = null;
		Ec ec = null;
		if (user != null){
			role = user.getRole();
			ec = role.getEc();
			info.addRole(role.getRoleName());
			
			// 管理员级别
			Role parentRole = role.getParentRole();
			if (parentRole == null) {
				shiroUser.setRoleLevel(1); // Sys-admin
			} else { 
				Role parentRole2 = parentRole.getParentRole();
				if (parentRole2 == null){
					shiroUser.setRoleLevel(2); // Ec-admin
				}
			}
			
			Set<Long> aboveRoleId = new HashSet<Long>();
			shiroManager.findAboveRoleIds(aboveRoleId , role);
			shiroUser.setAboveRoleId(aboveRoleId);
			
			if (ec != null) {
				Set<Long> aboveRoleIdSameEc = new HashSet<Long>();
				shiroManager.findAboveRoleIds(aboveRoleIdSameEc , role ,ec.getId());
				shiroUser.setAboveRoleIdSameEc(aboveRoleIdSameEc);
			}
			
			Set<Long> lowerRoleId = new HashSet<Long>();
			shiroManager.findLowerRoleIds(lowerRoleId , role);
			shiroUser.setLowerRoleId(lowerRoleId);
			
			shiroUser.setRoleId(role.getId());
			
			if (ec != null){
				shiroUser.setEcId(ec.getId());
				shiroUser.setCorpName(ec.getCorpName());
			}
			
			Set<String> permisStr = new HashSet<String>();
			Set<Action> actions = role.getAction();
			for (Action ac : actions){
				permisStr.add(ac.getActionName());
			}
			info.setStringPermissions(permisStr);
			shiroUser.setPermissions(permisStr);
		}
		return info;
	}
	
	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(UserService.HASH_ALGORITHM);
		matcher.setHashIterations(UserService.HASH_INTERATIONS);

		setCredentialsMatcher(matcher);
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public Long id;
		public String loginName;
		public String name;
		public Long ecId;
		public String corpName;
		private Long roleId;
		private int roleLevel = -1;
		private Set<Long> aboveRoleId = new HashSet<Long>();
		private Set<Long> aboveRoleIdSameEc = new HashSet<Long>();
		private Set<Long> lowerRoleId = new HashSet<Long>();
		private Set<String> permissions = new HashSet<String>();
		 
		public ShiroUser(Long id, String loginName, String name) {
			this.id = id;
			this.loginName = loginName;
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public Long getId() {
			return id;
		}

		public String getLoginName() {
			return loginName;
		}

		public Long getRoleId() {
			return roleId;
		}

		public Set<Long> getAboveRoleId() {
			return aboveRoleId;
		}

		public Set<Long> getLowerRoleId() {
			return lowerRoleId;
		}

		public void setLowerRoleId(Set<Long> lowerRoleId) {
			this.lowerRoleId = lowerRoleId;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public void setLoginName(String loginName) {
			this.loginName = loginName;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setRoleId(Long roleId) {
			this.roleId = roleId;
		}

		public void setAboveRoleId(Set<Long> aboveRoleId) {
			this.aboveRoleId = aboveRoleId;
		}

		public Long getEcId() {
			return ecId;
		}

		public void setEcId(Long ecId) {
			this.ecId = ecId;
		}

		public String getCorpName() {
			return corpName;
		}

		public void setCorpName(String corpName) {
			this.corpName = corpName;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(loginName);
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			ShiroUser other = (ShiroUser) obj;
			if (loginName == null) {
				if (other.loginName != null) {
					return false;
				}
			} else if (!loginName.equals(other.loginName)) {
				return false;
			}
			return true;
		}

		public Set<Long> getAboveRoleIdSameEc() {
			return aboveRoleIdSameEc;
		}

		public void setAboveRoleIdSameEc(Set<Long> aboveRoleIdSameEc) {
			this.aboveRoleIdSameEc = aboveRoleIdSameEc;
		}

		public Set<String> getPermissions() {
			return permissions;
		}

		public void setPermissions(Set<String> permissions) {
			this.permissions = permissions;
		}

		public int getRoleLevel() {
			return roleLevel;
		}

		public void setRoleLevel(int roleLevel) {
			this.roleLevel = roleLevel;
		}
		
	}
	
	
}
