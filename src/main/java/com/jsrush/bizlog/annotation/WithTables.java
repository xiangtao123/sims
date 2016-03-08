package com.jsrush.bizlog.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 展示业务实体:[{业务信息}]
 * @author sunburst
 *
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Documented
public @interface WithTables {
	
	public static final String SPLITOR = ", ";
	
	/**
	 * Columns : select
	 * @return
	 */
	WithTable[] withWithTables() default {};
	
	/**
	 * 分隔符
	 * @return
	 */
	String splitor() default WithTables.SPLITOR;
	
}
