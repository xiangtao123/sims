package com.jsrush.bizlog.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.jsrush.bizlog.constants.BizLogActionType;

/**
 * 展示业务实体：{字段信息}
 * @author sunburst
 *
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Documented
public @interface WithTable {
	
	public static final String ALIAS = "tbl";
	
	public static final String SPLITOR = ". ";
	
	/**
	 * table name : Entity name
	 * @return
	 */
	String name() default BizLogActionType.TEMPSTR;
	
	/**
	 * tabe name alias
	 * @return
	 */
	String alias() default WithTable.ALIAS;
	
	/**
	 * Columns : select
	 * @return
	 */
	WithColumn[] withColumns() default {};
	
	/**
	 * Columns : where condition
	 * @return
	 */
	ConditionColumn[] conditionColumns() default {};
	
	/**
	 * 分隔符
	 * @return
	 */
	String splitor() default WithTable.SPLITOR;
	
}
