package com.jsrush.security.rbac.vo;

/**
 * 企业集团信息
 * @author sunburst
 *
 */
public class EcDTO {

	private Long id;

	private String corpName;//集团客户名称

	private String corpAccount;//集团客户帐号
	
	private String email;//集团邮箱地址
	
	private String phoneNum;//集团联系人号码

	private String linkMan;//集团联系人

	private int actionState;//集团受理标志 1 : 订购 2 : 暂停 3 : 恢复 4 : 变更 5 : 退订

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public int getActionState() {
		return actionState;
	}

	public void setActionState(int actionState) {
		this.actionState = actionState;
	}

}
