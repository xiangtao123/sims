package com.jsrush.security.rbac.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OnlineUserListener implements ServletContextListener, HttpSessionListener {
	
	public static final String SESSION_USERCOUNT_KEY = "onlineUserCount";
	
	private static Logger log = LoggerFactory.getLogger(OnlineUserListener.class);
	
	public void updateOnlineUserCount(HttpSessionEvent httpSessionEvent, int count) {
		HttpSession session = httpSessionEvent.getSession();
		if (session == null)
			return;
		Integer onlineUserCount = (Integer) session.getServletContext().getAttribute(SESSION_USERCOUNT_KEY);
		if (onlineUserCount == null) {
			onlineUserCount = 0;
		}
		onlineUserCount += count;
		if (onlineUserCount < 0) {
			onlineUserCount = 0;
		}
		ServletContext servletContext = session.getServletContext();
		servletContext.setAttribute(SESSION_USERCOUNT_KEY, onlineUserCount);
		log.info(" session onlineUserCount is : "+onlineUserCount+",  change is : " + count);
	}
	
	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		updateOnlineUserCount(httpSessionEvent, 1);
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		updateOnlineUserCount(httpSessionEvent, -1);
	}
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		log.info(" contextInitialized:"+sce.getServletContext().getServerInfo());
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		log.info(" contextDestroyed:"+sce.getServletContext().getServerInfo());
	}
	
}
