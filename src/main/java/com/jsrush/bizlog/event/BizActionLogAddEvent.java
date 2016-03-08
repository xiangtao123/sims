package com.jsrush.bizlog.event;

import org.springframework.context.ApplicationEvent;

import com.jsrush.bizlog.entity.BizActionLog;

/**
 * 事件:新增业务操作日志
 * @author sunburst
 *
 */
public class BizActionLogAddEvent extends ApplicationEvent{

	private static final long serialVersionUID = 5384534024214977944L;

	private BizActionLog bizActionLog;
	
	public BizActionLogAddEvent(Object source, BizActionLog bizActionLog) {
		super(source);
		this.bizActionLog = bizActionLog;
	}
	
	public BizActionLog getBizActionLog() {
		return bizActionLog;
	}

	public void setBizActionLog(BizActionLog bizActionLog) {
		this.bizActionLog = bizActionLog;
	}
	
}
