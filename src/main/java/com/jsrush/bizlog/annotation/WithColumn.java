package com.jsrush.bizlog.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.jsrush.bizlog.constants.BizLogActionType;

/**
 * 展示字段：字段显示名称:字段数据内容
 * select filed info
 * @author sunburst
 *
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithColumn {

//	卡号#{obj.card}&卡类型#{staffCardCategory:obj.cardCategory}&卡位数#{staffCardDigit:obj.cardDigit}&卡状态#{staffCardState:obj.cardState}
	
	public static final String SPLITOR = ";";
	
	public static final String JOINOR = ":";
	
	/**
	 * 数据列显示名称
	 * @return
	 */
	String text() default BizLogActionType.TEMPSTR;
	
	/**
	 * 数据列字段名称: Column Name - entity.property
	 * @return
	 */
	String name() default BizLogActionType.TEMPSTR;
	
	/**
	 * 链接符
	 * @return
	 */
	String joinor() default WithColumn.JOINOR;
	
	/**
	 * 分割符
	 * @return
	 */
	String splitor() default WithColumn.SPLITOR;
	
	
	
}
