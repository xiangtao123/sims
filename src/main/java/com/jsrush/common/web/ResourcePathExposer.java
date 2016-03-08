package com.jsrush.common.web;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

/**
 * 静态资源逻辑目录
 * 发布新版本的应用时，根据客户端浏览器本身的缓存机制更新静态资源
 * 
 * @author sunburst
 *
 */
public class ResourcePathExposer implements ServletContextAware {

	private ServletContext servletContext;
	
	private String resourceRoot;

	private String resourceVersion="";
	
	public void init() {
		resourceRoot = "/resources/version-" + resourceVersion;
		servletContext.setAttribute("resourceRoot", servletContext.getContextPath() + resourceRoot);
	}
	
	@Override
	public void setServletContext(ServletContext serveletCxt) {
		this.servletContext = serveletCxt;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public String getResourceRoot() {
		return resourceRoot;
	}

	public String getResourceVersion() {
		return resourceVersion;
	}

	public void setResourceVersion(String resourceVersion) {
		this.resourceVersion = resourceVersion;
	}
	
}
