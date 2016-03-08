package com.jsrush.bizlog.event;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.jsrush.bizlog.entity.BizActionLog;
import com.jsrush.common.service.SpringContextUtil;

/**
 * 事件发布:新增业务操作日志
 * @author sunburst
 *
 */
@Component
public class BizActionLogAddEventPublisher {

	public void publishEvent(BizActionLog bizActionLog){
		ApplicationContext applicationContext = SpringContextUtil.getCtx();
		BizActionLogAddEvent event = new BizActionLogAddEvent(applicationContext, bizActionLog);
		applicationContext.publishEvent(event);
	}
	
}
