package com.jsrush.bizlog.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jsrush.bizlog.constants.BizLogActionType;

/**
 * 业务日志
 * @author sunburst
 *
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ActionLog {

	public static final String BIZINFOEXP_SPLITOR = ",";
	
	public static final String RETURNVALUE = "returnValue";
	/**
	 * 操作类型
	 * @return
	 */
	String actionType() default BizLogActionType.TEMPSTR;
	
	/**
	 * 业务类型
	 * @return
	 */
	String bizType() default BizLogActionType.TEMPSTR;
	
	/**
	 * 期望返回值表达式
	 * @return
	 */
	String expectedExp() default BizLogActionType.TEMPSTR;
	
	/**
	 * 业务标识信息表达式
	 * @return
	 */
	String bizInfoExp() default BizLogActionType.TEMPSTR;
	

	
}
