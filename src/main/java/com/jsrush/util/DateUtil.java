package com.jsrush.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * 日期工具类
 * @author albert
 *
 */
public class DateUtil {
	public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd";
	public static final String COMMONE_DATE_FORMAT = "yyyy-M-d";
	public static final String NORMAL_MONTH_DATE_FORMAT = "MM-dd";
	public static final String NORMAL_SQL_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String NORMAL_TIME_FORMAT = "yyyy-MM-dd HH:mm";
	public static final String ADC_MSG_TIMESTAMP = "yyyyMMddHHmmssSSSS";
	public static final String ADC_MSG_SID = "yyyyMMddHHmmss";
	public static final String MONTH_DATE_FORMAT = "yyyy-MM";
	public static final String YEAR_DATE_FORMAT = "yyyy";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String HOUR_FORMAT = "HH:mm";
	public static final String TIME_SPLIT = ":";
	private static final String SPLIT_FLAG = "-";

	public static String getWeekFromDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		String week = (calendar.get(Calendar.DAY_OF_WEEK) - 1 == 0) ? "7" : String.valueOf(calendar.get(Calendar.DAY_OF_WEEK) - 1);
		return week;
	}

	public static final Date parseDate(String str, String fmt) throws ParseException {
		if (StringHelper.isEmpty(fmt)) {
			fmt = COMMONE_DATE_FORMAT;
		}
		SimpleDateFormat format = new SimpleDateFormat(fmt);
		return format.parse(str);
	}

	public static final java.sql.Date parseSQLDate(String str, String fmt) throws ParseException {
		if (StringHelper.isEmpty(fmt)) {
			fmt = COMMONE_DATE_FORMAT;
		}
		Date d = parseDate(str, fmt);
		return new java.sql.Date(d.getTime());
	}
	
	public static final java.sql.Date parseSQLDate(String str) throws ParseException {
		return parseSQLDate(str, DateUtil.NORMAL_DATE_FORMAT);
	}

	public static final String dateToString(Date date, String fmt) {
		if (StringHelper.isEmpty(fmt)) {
			fmt = NORMAL_DATE_FORMAT;
		}
		SimpleDateFormat format = new SimpleDateFormat(fmt);
		return format.format(date);
	}

	public static final String formatTimestampToStringByFmt(Timestamp timestamp, String fmt) {

		if (timestamp == null) {
			return null;
		}

		SimpleDateFormat format = new SimpleDateFormat(fmt);
		return format.format(timestamp);
	}

	public static final Timestamp formatStringToTimestampByFmt(String time, String fmt) {

		if (time == null || time == "")
			return null;

		SimpleDateFormat df = new SimpleDateFormat(fmt);
		Date date;
		try {
			date = df.parse(time);
			Timestamp registerDate = new Timestamp(date.getTime());
			return registerDate;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static final String dateToMonthString(Date date, String fmt) {
		if (StringHelper.isEmpty(fmt)) {
			fmt = MONTH_DATE_FORMAT;
		}
		SimpleDateFormat format = new SimpleDateFormat(fmt);
		return format.format(date);
	}

	public static Timestamp getNowTimestamp() {
		long curTime = System.currentTimeMillis();
		return new Timestamp(curTime);
	}

	public static float getHourFromStringTimeCrossDay(String startTime, String endTime) {
		float resultHour = 0;
		if (!StringHelper.isEmpty(startTime) && !StringHelper.isEmpty(endTime)) {
			String[] startTimes = startTime.split("[:]");
			String[] endTimes = endTime.split("[:]");
			Integer endHourTime = Integer.parseInt(endTimes[0]) * 60 + Integer.parseInt(endTimes[1]);
			Integer middleTime = Integer.parseInt("24") * 60 + 0;
			Integer startHourTime = Integer.parseInt(startTimes[0]) * 60 + Integer.parseInt(startTimes[1]);
			resultHour = (float) ((middleTime - startHourTime) + endHourTime) / 60;
			return resultHour;
		}
		return resultHour;
	}

	/**
	 * 获取两个时段的小时差
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static float getHourFromStringTime(String startTime, String endTime) {
		float resultHour = 0;
		if (!StringHelper.isEmpty(startTime) && !StringHelper.isEmpty(endTime)) {
			String[] startTimes = startTime.split("[:]");
			String[] endTimes = endTime.split("[:]");
			Integer endHourTime = Integer.parseInt(endTimes[0]) * 60 + Integer.parseInt(endTimes[1]);
			Integer startHourTime = Integer.parseInt(startTimes[0]) * 60 + Integer.parseInt(startTimes[1]);
			resultHour = (float) (endHourTime - startHourTime) / 60;
			return resultHour;
		}
		return resultHour;
	}

	/**
	 * 该方法稍作修改，返回跨天时段计算迟到方法，精确到秒
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static float getMinFromStringTimeCrossDay(String startTime, String endTime) {
		float resultHour = 0;
		if (!StringHelper.isEmpty(startTime) && !StringHelper.isEmpty(endTime)) {
			String[] startTimes = startTime.split("[:]");
			String[] endTimes = endTime.split("[:]");
			Integer endHourTime = Integer.parseInt(endTimes[0]) * 60 + Integer.parseInt(endTimes[1]);
			Integer middleTime = Integer.parseInt("24") * 60 + 0;
			Integer startHourTime = Integer.parseInt(startTimes[0]) * 60 + Integer.parseInt(startTimes[1]);
			resultHour = (float) ((middleTime - startHourTime) + endHourTime);
			return resultHour;
		}
		return resultHour;
	}

	/**
	 * 该方法返回跨天迟到计算方法，精确到秒
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static int getSecondFromStringTimeCrossDay(String startTime, String endTime) {
		int resultSecond = 0;
		if (!StringHelper.isEmpty(startTime) && !StringHelper.isEmpty(endTime)) {
			String[] startTimes = startTime.split("[:]");
			String[] endTimes = endTime.split("[:]");
			Integer endHourTime = Integer.parseInt(endTimes[0]) * 3600 + Integer.parseInt(endTimes[1]) * 60;
			Integer middleTime = Integer.parseInt("24") * 3600 + 0;
			Integer startHourTime = Integer.parseInt(startTimes[0]) * 3600 + Integer.parseInt(startTimes[1]) * 60;
			resultSecond = (middleTime - startHourTime) + endHourTime;
			return resultSecond;
		}
		return resultSecond;
	}

	/**
	 * 将timestamp转换为ADC消息头的对应格式，如果参数为空则取当前系统时间
	 * @param time
	 * @return
	 */
	public static String getADCMsgHeadTimestamp(Timestamp time) {
		if (time == null) {
			time = new Timestamp(System.currentTimeMillis());
		}
		SimpleDateFormat format = new SimpleDateFormat(ADC_MSG_TIMESTAMP);
		return format.format(time);
	}

	public static String getADCMsgSIDTimestamp(Timestamp time) {
		if (time == null) {
			time = new Timestamp(System.currentTimeMillis());
		}
		SimpleDateFormat format = new SimpleDateFormat(ADC_MSG_SID);
		return format.format(time);
	}

	public static int getWeekByDate(Date date) {
		if (date == null)
			return -1;
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 将获取的long型日期去掉时间
	 * @param timestamp
	 * @return
	 */
	public static Timestamp formatTimestampRemoveHours(long timestamp) {

		String removeHoursTimestamp = DateUtil.formatTimestampToStringByFmt(new Timestamp(timestamp), NORMAL_DATE_FORMAT);

		return Timestamp.valueOf(removeHoursTimestamp + " 00:00:00");
	}

	public static Timestamp getTimeByZKTimestamp(long time) {
		long second = time % 60; //[0,59]
		time /= 60;
		long minute = time % 60; //[0,59]
		time /= 60;
		long hour = time % 24; //[0,23]
		time /= 24;
		long date = time % 31 + 1; //[1,31]
		time /= 31;
		long month = time % 12; //[0,11]
		time /= 12;
		long year = time + 100; //1900开始
		year = year + 1900;
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
		cal.set((int) year, (int) month, (int) date, (int) hour, (int) minute, (int) second);
		return new Timestamp(cal.getTimeInMillis());
	}

	public static long getZKTimestamp(Timestamp time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time.getTime());
		long year = cal.get(Calendar.YEAR) - 1900;
		long month = cal.get(Calendar.MONTH);
		long day = cal.get(Calendar.DAY_OF_MONTH);
		long hour = cal.get(Calendar.HOUR_OF_DAY);
		long minute = cal.get(Calendar.MINUTE);
		long second = cal.get(Calendar.SECOND);
		long zktime = ((year - 100) * 12 * 31 + month * 31 + day - 1) * (24 * 60 * 60) + (hour * 60 + minute) * 60 + second;
		return zktime;
	}

	/**
	 * 根据传入的日期和有效天数计算自传入的日期起到有效期结束的日期
	 * @param date 日期
	 * @param duration 有效期
	 * @return
	 */
	public static Date[] getDurationDate(Date date, int duration) {

		if (duration == 0) {
			return null;
		}

		Date[] dates = new Date[duration];
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		for (int i = 0; i < duration; ++i) {
			if (i != 0) {
				c.add(Calendar.DATE, 1);
			}
			dates[i] = c.getTime();
		}

		return dates;
	}

	/**
	 * 计算两天之间相差的天数
	 *
	 * @param strBegin
	 * @param strEnd
	 * @return
	 */
	public static int returnCalculatedDifferenceDate(String strBegin, String strEnd) {
		SimpleDateFormat f = new SimpleDateFormat(NORMAL_DATE_FORMAT);
		Date date1 = null, date2 = null;
		int days = 0;
		try {
			date1 = f.parse(strBegin);
			date2 = f.parse(strEnd);
			days = (int) ((date2.getTime() - date1.getTime()) / 86400000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return days + 1;
	}

	/**
	 * 获取当前日期所在周的第一天（系统认定星期一为每周第一天或者星期日为每周第一天）
	 * @param date
	 * @param firstDayIsMon 周一是否是week的第一天
	 * @return
	 */
	public static Date getWeekFirstDay(String date, boolean firstDayIsMon) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(java.sql.Date.valueOf(date).getTime());
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		if (firstDayIsMon) {
			dayOfWeek = dayOfWeek - 1;
			if (dayOfWeek == 0)
				dayOfWeek = 7;
		}
		cal.add(Calendar.DATE, 1 - dayOfWeek);
		return cal.getTime();
	}

	/**
	 * 检查两个时段区间是否冲突
	 * 开始时间与结束时间相同的情况 将视为不冲突
	 * @param startTime
	 * @param endTime
	 * @param startTime2
	 * @param endTime2
	 * @return 1不冲突 0冲突
	 */
	public static Integer checkTimeRange(String startTime, String endTime, String startTime2, String endTime2) {
		boolean isTimeAcross1 = startTime.compareTo(endTime) > 0;
		boolean isTimeAcross2 = startTime2.compareTo(endTime2) > 0;
		if (isTimeAcross1 && isTimeAcross2) {
			return 0;
		} else if (isTimeAcross1) {
			Integer checkTimeRange = checkTimeRange("00:00", endTime, startTime2, endTime2);
			if (checkTimeRange == 0) {
				return 0;
			}
		} else if (isTimeAcross2) {
			Integer checkTimeRange = checkTimeRange(startTime, endTime, "00:00", endTime2);
			if (checkTimeRange == 0) {
				return 0;
			}
		}
		endTime = isTimeAcross1 ? "23:59" : endTime;
		endTime2 = isTimeAcross2 ? "23:59" : endTime2;
		if ((startTime.compareTo(startTime2) < 0 && endTime.compareTo(startTime2) > 0)
				|| (startTime.compareTo(endTime2) < 0 && endTime.compareTo(endTime2) > 0)) {
			return 0;
		} else if ((startTime2.compareTo(startTime) < 0 && endTime2.compareTo(startTime) > 0)
				|| (startTime2.compareTo(endTime) < 0 && endTime2.compareTo(endTime) > 0)) {
			return 0;
		} else if ((startTime.compareTo(startTime2) < 0 && endTime.compareTo(endTime2) > 0)
				|| (startTime2.compareTo(startTime) < 0 && endTime2.compareTo(endTime) > 0)) {
			return 0;
		}
		return 1;
	}

	public static String[] getTimeRangeWithMinute(String time, int range) {
		if (StringHelper.isEmpty(time))
			return null;
		String[] times = time.split(TIME_SPLIT);
		if (times.length >= 2) {
			try {
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.MINUTE, Integer.parseInt(times[1]));
				cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(times[0]));
				cal.set(Calendar.SECOND, 59);
				cal.add(Calendar.MINUTE, range);
				String max = DateUtil.dateToString(cal.getTime(), TIME_FORMAT);
				cal.set(Calendar.SECOND, 00);
				cal.add(Calendar.MINUTE, range * -2);
				String min = DateUtil.dateToString(cal.getTime(), TIME_FORMAT);
				String[] result = new String[2];
				result[0] = min;
				result[1] = max;
				return result;
			} catch (Exception e) {

			}
		}
		return null;
	}

	/**
	 * 计算从给定日期，给定天数的目标日期
	 *
	 * @param today
	 * @param beforeDay 如果是正数则为给定日期之后的日期
	 * 					如果是负数则为给定日期之前的日期
	 * @return
	 * @throws ParseException
	 */
	public static String calculationBeforeToday(String today, int beforeDay) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT);
		Date date = sdf.parse(today);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, beforeDay);
		return sdf.format(c.getTime());
	}

	public static int compareDate(String date1, String date2) {
		SimpleDateFormat sdf = new SimpleDateFormat(NORMAL_DATE_FORMAT);

		try {
			Date date = sdf.parse(date1);
			Date dateTo = sdf.parse(date2);

			return date.compareTo(dateTo);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 获取当月最小(早)的Timestamp
	 * @param month
	 * @return
	 */
	public static Timestamp getMonthBeginTimestamp(String month) {
		return Timestamp.valueOf(month + "-01 00:00:00.000");
	}

	/**
	 * 获取当月最大(晚)的Timestamp
	 * @param month
	 * @return
	 */
	public static Timestamp getMonthEndTimestamp(String month) {
		Timestamp beginTimestamp = getMonthBeginTimestamp(month);
		return Timestamp.valueOf(DateUtil.dateToString(DateUtils.addDays(DateUtils.addMonths(beginTimestamp, 1), -1), DateUtil.NORMAL_DATE_FORMAT
				+ " 23:59:59.999"));
	}

	/**
	 * 该方法用于返回月数组
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	public static String[] getMonthesByFromAndTo(String startMonth, String endMonth) {
		if (StringHelper.isEmpty(startMonth) || StringHelper.isEmpty(endMonth))
			return null;
		try {
			String[] startMonthes = startMonth.split(SPLIT_FLAG);
			String[] endMonthes = endMonth.split(SPLIT_FLAG);
			int sty = Integer.parseInt(startMonthes[0]);
			int eny = Integer.parseInt(endMonthes[0]);
			int stm = Integer.parseInt(startMonthes[1]);
			int enm = Integer.parseInt(endMonthes[1]);
			if (eny - sty >= 2)//控制在1年之内
				return null;
			if (eny - sty == 1 && enm >= stm)
				return null;
			String[] resultMonthes = null;
			String resultMonth = "";
			if (sty == eny) {//表示同一年
				resultMonthes = new String[enm - stm + 1];
				for (int i = stm, r = 0; r < enm - stm + 1; i++, r++) {
					resultMonth = sty + SPLIT_FLAG + (i < 10 ? "0" + i : i);
					resultMonthes[r] = resultMonth;
				}
			} else {
				int result = 13 - stm + enm;
				resultMonthes = new String[result];
				for (int i = stm, r = 0; r < result; i++, r++) {
					if (i == 13) {
						sty = eny;
						i = 1;
					}
					resultMonth = sty + SPLIT_FLAG + (i < 10 ? "0" + i : i);
					resultMonthes[r] = resultMonth;
				}
			}
			return resultMonthes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String[] getNextMontheDatesByMonth(String[] monthes, String date) {
		if (monthes == null || StringHelper.isEmpty(date))
			return null;
		String[] result = new String[monthes.length];
		int newY = 0;
		int newM = 0;
		int my = 0;
		int mm = 0;
		String resultDate = "";
		for (int i = 0; i < monthes.length; i++) {
			String[] split = monthes[i].split(SPLIT_FLAG);
			my = Integer.parseInt(split[0]);
			mm = Integer.parseInt(split[1]);
			newY = my;
			newM = mm + 1;
			if (mm == 12) {
				newM = 1;
				newY = my + 1;
			}
			resultDate = newY + SPLIT_FLAG + (newM < 10 ? "0" + newM : newM) + SPLIT_FLAG + date;
			result[i] = resultDate;
		}
		return result;
	}

	/**
	 * 根据传来的日期format为中文格式
	 * @param date 传来的日期
	 * @return 返回转换后的日期
	 */
	public static String formatDateForType(Date date) {
		String result = "";
		if (date == null)
			return result;
		String dateToString = dateToString(date, NORMAL_MONTH_DATE_FORMAT);
		if (dateToString == null)
			return result;
		String[] split = dateToString.split("-");
		result = split[0] + "月" + split[1] + "日";
		return result;
	}
}
