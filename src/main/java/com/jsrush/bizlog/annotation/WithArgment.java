package com.jsrush.bizlog.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.jsrush.bizlog.constants.BizLogActionType;

/**
 * 展示业务信息：from json string
 * @author sunburst
 *
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Documented
public @interface WithArgment {

	public static final int ARGINDEX = 0;

	public static final String JOINOR = ":";

	public static final int OBJECT = 0;
	
	public static final int JSONSTR = 1;
	
	public static final String RETURNVALUE = "RETURNVALUE";
	
	public static final String ALINK = "ALINK";
	
	/**
	 * 属性显示名称
	 * @return
	 */
	String text() default BizLogActionType.TEMPSTR;
	
	/**
	 * 参数位置
	 * @return
	 */
	int argIndex() default WithArgment.ARGINDEX;
	
	/**
	 * 参数类型
	 * @return
	 */
	int argType() default WithArgment.JSONSTR;
	
	/**
	 * 对象属性表达式
	 * @return
	 */
	String argExpress() default BizLogActionType.TEMPSTR;
	
	/**
	 * 链接符
	 * @return
	 */
	String joinor() default WithArgment.JOINOR;
	
}
