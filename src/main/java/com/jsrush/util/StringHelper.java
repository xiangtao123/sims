package com.jsrush.util;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StringHelper {
	private static final Log log = LogFactory.getLog(StringHelper.class);

	public static boolean isEmpty(String string) {
		return string == null || string.trim().equals("");
	}

	public static boolean isNotEmpty(String string) {
		return !isEmpty(string);
	}
	
	public static boolean stringEquals(String s1, String s2) {
		if (s1 == null) {
			s1 = "";
		}
		if (s2 == null) {
			s2 = "";
		}
		return s1.equals(s2);
	}

	public static String formatName(String firstname, String surname) {
		if (surname != null && surname.length() > 0 && surname.charAt(0) > 127) // 姓为中文
			return surname + (StringHelper.isEmpty(firstname) ? "" : (firstname));
		else
			// 姓为英文
			return (StringHelper.isEmpty(firstname) ? "" : (firstname + " ")) + surname;
	}

	/**
	 * 清除空白字符
	 * 包括 空格、回车、换行符、制表符
	 * @param string
	 * @return
	 */
	public static String removeBlank(String string) {
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		Matcher m = p.matcher(string);
		return m.replaceAll("");
	}

	public static String join(Object[] objs, String separator) {
		if (objs == null || objs.length == 0 || separator == null)
			return null;
		StringBuilder sb = new StringBuilder();
		for (Object obj : objs) {
			if (obj == null)
				continue;
			sb.append(separator).append(obj.toString());
		}
		return sb.toString().substring(separator.length());
	}

	@SuppressWarnings("rawtypes")
	public static String join(Collection objs, String separator) {
		if (objs == null || objs.size() == 0 || separator == null)
			return null;
		StringBuilder sb = new StringBuilder(256);
		for (Object obj : objs) {
			if (obj == null)
				continue;
			sb.append(separator).append(obj.toString());
		}
		return sb.toString().substring(separator.length());
	}

	public static String joinIds(Collection<Long> ids, String separator) {
		if (ids == null || ids.size() == 0 || separator == null)
			return null;
		Iterator<Long> itr = ids.iterator();
		Long first = itr.next();
		StringBuilder sb = new StringBuilder(ids.size() * (first.toString().length() + 1));
		sb.append(first);
		while (itr.hasNext())
			sb.append(separator).append(itr.next());
		return sb.toString();
	}

	public static String joinIds(Long[] ids, String separator) {
		if (ids == null || ids.length == 0 || separator == null)
			return null;
		StringBuilder sb = new StringBuilder(ids.length * (ids[0].toString().length() + 1));
		sb.append(ids[0]);
		for (int i = 1; i < ids.length; i++)
			sb.append(separator).append(ids[i]);
		return sb.toString();
	}

	/**
	 * 数字转换
	 * @param id  数字串
	 * @param radix 该数字串的进制
	 * @param digi  需要转换后的位数
	 * @return
	 */
	public static String smardIdConvert(String id, int radix, int digi) {
		try {
			if (StringUtils.isBlank(id))
				return null;
			long i = 0;
			if (radix == 16) {
				if (digi == 10)
					id += "000000";
				BigInteger bigInt = new BigInteger(id, 16);
				i = bigInt.longValue();
			} else
				i = Long.parseLong(id, radix);
			if (radix == 10) {
				String hex = Long.toHexString(Long.reverseBytes(i));
				if (hex.length() < 16)
					hex = buString(hex, 16);
				return hex.substring(0, digi);
			} else if (radix == 16) {
				return buString(String.valueOf(Long.reverseBytes(i)), digi);
			}
		} catch (Exception e) {
			log.error(LogUtil.stackTraceToString(e));
		}
		return null;
	}

	public static String buString(String str, Integer lenght) {
		StringBuilder s = new StringBuilder(str);
		while (s.length() < lenght) {
			s.insert(0, "0");
		}
		return s.toString();
	}

	public static String listToString(List<Long> permission) {
		StringBuilder builder = new StringBuilder();
		builder.append(" ( ");
		for (int i = 0; i < permission.size(); i++) {
			if (permission.size() == 1) {
				return builder.append(permission.get(0)).toString();
			}
			builder.append(permission.get(i)).append(",");
		}
		builder.append(" ) ");
		String str = builder.deleteCharAt(builder.length() - 4).toString();
		return str;
	}
}
