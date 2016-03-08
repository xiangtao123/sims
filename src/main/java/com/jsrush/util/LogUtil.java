package com.jsrush.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LogUtil {
	/**
	 * 打印异常的堆栈信息 赵志凌 20050421
	 * 
	 * @param p_oException
	 * @return
	 */
	public static String stackTraceToString(Throwable e) {
		StringWriter l_oSw = new StringWriter();
		e.printStackTrace(new PrintWriter(l_oSw));
		return l_oSw.toString();
	}
}