package com.jsrush.common.config;

/**
 * 系统配置信息
 * @author sunburst
 *
 */
public class SystemConfig {

	private String webContext = "gongzi";
	
	private String apiServerUrl = "http://192.168.1.103/api/";

	private String uploadFolder = "upload";
	
	private String alinkTpl = "<a href=\"url\" style=\"color:blue;\" target=\"_blank\">text</a>";
	
	public String getWebContext() {
		return webContext;
	}

	public String getApiServerUrl() {
		return apiServerUrl;
	}

	public void setWebContext(String webContext) {
		this.webContext = webContext;
	}

	public void setApiServerUrl(String apiServerUrl) {
		this.apiServerUrl = apiServerUrl;
	}

	public String getUploadFolder() {
		return uploadFolder;
	}

	public void setUploadFolder(String uploadFolder) {
		this.uploadFolder = uploadFolder;
	}

	public String getAlinkTpl() {
		return alinkTpl;
	}

	public void setAlinkTpl(String alinkTpl) {
		this.alinkTpl = alinkTpl;
	}
	
}
