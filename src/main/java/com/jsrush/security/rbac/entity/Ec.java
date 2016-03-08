package com.jsrush.security.rbac.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.jsrush.common.entity.IdEntity;
import com.jsrush.util.TimestampJsonSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name="t_jsrush_ec")
public class Ec extends IdEntity {
	
	private String corpName;//集团客户名称

	private String corpAccount;//集团客户帐号

	private String license;//业务License

	private String shortName;//集团简称

	private String englishName;//集团英文全称

	private String eshortName;//集团英文简称

	private String postCode;//集团邮政编码

	private String mgrLinkNo;//企业管理员联系电话

	private String customerMng;//客户经理姓名

	private String customerMngNo;//客户经理电话

	private String fax;//集团传真号码

	private String email;//集团邮箱地址

	private String cardid;//集团证件号

	private String wwwUrl;//企业web网址

	private String wapUrl;//企业wap网址

	private String groupLogoUrl;//企业LOGO URL

	private String linkMan;//集团联系人

	private String phoneNum;//集团联系人号码

	private Integer actionState;//集团受理标志 1 : 订购 2 : 暂停 3 : 恢复 4 : 变更 5 : 退订

	private String bizcode;//服务代码
	
	private String corpCode;//企业代码，唯一
	
	private String registrationNo;//企业注册码，唯一

	private Set<Role>roles=new HashSet<Role>();
	
	private Timestamp createTime;
	private Timestamp updateTime;
	
	
	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "t_jsrush_ec_permissions", joinColumns = { @JoinColumn(name = "ec_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	@JsonSerialize(using = TimestampJsonSerializer.class)
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	@JsonSerialize(using = TimestampJsonSerializer.class)
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getCorpAccount() {
		return corpAccount;
	}

	public void setCorpAccount(String corpAccount) {
		this.corpAccount = corpAccount;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getEshortName() {
		return eshortName;
	}

	public void setEshortName(String eshortName) {
		this.eshortName = eshortName;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getMgrLinkNo() {
		return mgrLinkNo;
	}

	public void setMgrLinkNo(String mgrLinkNo) {
		this.mgrLinkNo = mgrLinkNo;
	}

	public String getCustomerMng() {
		return customerMng;
	}

	public void setCustomerMng(String customerMng) {
		this.customerMng = customerMng;
	}

	public String getCustomerMngNo() {
		return customerMngNo;
	}

	public void setCustomerMngNo(String customerMngNo) {
		this.customerMngNo = customerMngNo;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCardid() {
		return cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	public String getWwwUrl() {
		return wwwUrl;
	}

	public void setWwwUrl(String wwwUrl) {
		this.wwwUrl = wwwUrl;
	}

	public String getWapUrl() {
		return wapUrl;
	}

	public void setWapUrl(String wapUrl) {
		this.wapUrl = wapUrl;
	}

	public String getGroupLogoUrl() {
		return groupLogoUrl;
	}

	public void setGroupLogoUrl(String groupLogoUrl) {
		this.groupLogoUrl = groupLogoUrl;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public Integer getActionState() {
		return actionState;
	}

	public void setActionState(Integer actionState) {
		this.actionState = actionState;
	}

	public String getBizcode() {
		return bizcode;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}

	public String getCorpCode() {
		return corpCode;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
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
