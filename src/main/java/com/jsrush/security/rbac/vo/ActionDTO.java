package com.jsrush.security.rbac.vo;

/**
 * 系统资源
 * @author sunburst
 *
 */
public class ActionDTO {
	
	private Long id;
	
	private String key;					// 主键名称

	private Long pId;						//	上级

	private String name;					//	显示的名字
	
	private String type;					// 资源类型：模块：module, 页面：page，元素：element
	
	private String targetUrl;			// 类型为页面时，对应的页面访问地址
	
	private String title;				// 提示信息
	
	private Integer idx = 0;			// 顺序

	public String getKey() {
		return key;
	}

	public Long getpId() {
		return pId;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setpId(Long pId) {
		this.pId = pId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIdx() {
		return idx;
	}

	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	
}
