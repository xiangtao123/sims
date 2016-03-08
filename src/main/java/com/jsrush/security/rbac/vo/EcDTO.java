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

	private String wwwURL;//企业web网址

	private String wapURL;//企业wap网址

	private String groupLogoURL;//企业LOGO URL

	private String linkMan;//集团联系人

	private String phoneNum;//集团联系人号码

	private int actionState;//集团受理标志 1 : 订购 2 : 暂停 3 : 恢复 4 : 变更 5 : 退订

	private String bizcode;//服务代码
	
	private String corpCode;//企业代码，唯一
	
	private String registrationNo;//企业注册码，唯一
	
	public Long getId() {
		return id;
	}

	public String getCorpName() {
		return corpName;
	}

	public String getCorpAccount() {
		return corpAccount;
	}

	public String getLicense() {
		return license;
	}

	public String getShortName() {
		return shortName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public String getEshortName() {
		return eshortName;
	}

	public String getPostCode() {
		return postCode;
	}

	public String getMgrLinkNo() {
		return mgrLinkNo;
	}

	public String getCustomerMng() {
		return customerMng;
	}

	public String getCustomerMngNo() {
		return customerMngNo;
	}

	public String getFax() {
		return fax;
	}

	public String getEmail() {
		return email;
	}

	public String getCardid() {
		return cardid;
	}

	public String getWwwURL() {
		return wwwURL;
	}

	public String getWapURL() {
		return wapURL;
	}

	public String getGroupLogoURL() {
		return groupLogoURL;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public int getActionState() {
		return actionState;
	}

	public String getBizcode() {
		return bizcode;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public void setCorpAccount(String corpAccount) {
		this.corpAccount = corpAccount;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public void setEshortName(String eshortName) {
		this.eshortName = eshortName;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public void setMgrLinkNo(String mgrLinkNo) {
		this.mgrLinkNo = mgrLinkNo;
	}

	public void setCustomerMng(String customerMng) {
		this.customerMng = customerMng;
	}

	public void setCustomerMngNo(String customerMngNo) {
		this.customerMngNo = customerMngNo;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	public void setWwwURL(String wwwURL) {
		this.wwwURL = wwwURL;
	}

	public void setWapURL(String wapURL) {
		this.wapURL = wapURL;
	}

	public void setGroupLogoURL(String groupLogoURL) {
		this.groupLogoURL = groupLogoURL;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public void setActionState(int actionState) {
		this.actionState = actionState;
	}

	public void setBizcode(String bizcode) {
		this.bizcode = bizcode;
	}

	public String getCorpCode() {
		return corpCode;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setCorpCode(String corpCode) {
		this.corpCode = corpCode;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}
	
}
