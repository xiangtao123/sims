package com.jsrush.bizlog.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 展示业务信息：from json string
 * @author sunburst
 *
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Documented
public @interface WithArgments {
	
	public static final String SPLITOR = ", ";
	
	/**
	 * Columns : select
	 * @return
	 */
	WithArgment[] withArgments() default {};
	
	/**
	 * 分隔符
	 * @return
	 */
	String splitor() default WithArgments.SPLITOR;
	
}
