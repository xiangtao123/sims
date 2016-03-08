package com.jsrush.bizlog.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jsrush.bizlog.constants.BizLogActionType;

/**
 * where condition
 * @author sunburst
 *
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConditionColumn {

	public static final int ARGINDEX = 0;
	
	public static final int OBJECT = 0;
	
	public static final int JSONSTR = 1;

	public static final String EQUALS = "=";
	
	public static final String INLIST = "in";
	
	
	/**
	 * 参数位置
	 * @return
	 */
	int argIndex() default ConditionColumn.ARGINDEX;
	
	/**
	 * 对象属性表达式
	 * @return
	 */
	String argExpress() default BizLogActionType.TEMPSTR;
	
	/**
	 * 目标方法参数类型：object or json string
	 * @return
	 */
	int argType() default ConditionColumn.OBJECT;
	
	/**
	 * 数据列字段名称: Column Name - entity.property
	 * @return
	 */
	String name() default BizLogActionType.TEMPSTR;
	
	/**
	 * where 语句操作符
	 * @return
	 */
	String operator() default ConditionColumn.EQUALS;
	
}
