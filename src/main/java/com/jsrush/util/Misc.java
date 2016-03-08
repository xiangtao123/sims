package com.jsrush.util;

import net.sf.json.JSONObject;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Misc {

	public static final int SEVENDAYMINITES = 1 * 24 * 60;
	public static final String CLIENT_INVALID_DATE = "2099-12-31";
	private static final String[] alphabet = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
			"w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

	private static final String[] nums = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	private static final String LANG_ZH = "zh";
	private static final String[] Locale_Changes_For_ZH = { "12:", "0:", "下", "中" };

	public static String generateRandomNumber(int length) {
		StringBuilder sb = new StringBuilder();
		Random r = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(nums[r.nextInt(10)]);

		}
		return sb.toString();
	}

	public static String generateRandomAlphabet() {
		Random r = new Random();
		return new StringBuilder().append("").append(alphabet[r.nextInt(26)]).append(alphabet[r.nextInt(26)]).append(alphabet[r.nextInt(26)])
				.append(alphabet[r.nextInt(26)]).append(alphabet[r.nextInt(26)]).append(alphabet[r.nextInt(26)]).toString();
	}

	public static Timestamp getMinTimestamp() {
		Timestamp ts = Timestamp.valueOf("1930-01-01 00:00:00");
		return ts;
	}

	public static String offSetDate(final String date, int days) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String[] myDay = date.split("-");
		GregorianCalendar greCalendear = new GregorianCalendar();
		greCalendear.set(Calendar.YEAR, Integer.parseInt(myDay[0]));
		greCalendear.set(Calendar.MONTH, Integer.parseInt(myDay[1]) - 1);
		greCalendear.set(Calendar.DAY_OF_MONTH, Integer.parseInt(myDay[2]));
		System.out.println(df.format(greCalendear.getTime()));
		int day = greCalendear.get(Calendar.DAY_OF_MONTH);
		day += days;
		greCalendear.set(Calendar.DAY_OF_MONTH, day);
		return df.format(greCalendear.getTime());
	}

	public static Timestamp stringToTimestamp(String time) {
		try {
			Timestamp ts = Timestamp.valueOf(time);
			return ts;
		} catch (Exception e) {
			return Misc.getMinTimestamp();
		}
	}

	public static String getDate() {
		String temp = getSystemDateTime();
		int index = temp.indexOf(" ");
		temp = temp.substring(0, index);
		return temp;

	}

	public static String getSystemDateTime() {
		Calendar cal = Calendar.getInstance();
		StringBuilder sb = new StringBuilder();
		sb.append(cal.get(Calendar.YEAR));
		sb.append("-");
		sb.append(cal.get(Calendar.MONTH) + 1);
		sb.append("-");
		sb.append(cal.get(Calendar.DAY_OF_MONTH));
		sb.append(" ");
		sb.append(cal.get(Calendar.HOUR_OF_DAY));
		sb.append(":");
		sb.append(cal.get(Calendar.MINUTE));
		sb.append(":");
		sb.append(cal.get(Calendar.SECOND));
		return sb.toString();
	}

	public static String getSystemDateTimeForYMD() {
		Calendar cal = Calendar.getInstance();
		StringBuilder sb = new StringBuilder();
		sb.append(cal.get(Calendar.YEAR));
		sb.append("-");
		if (cal.get(Calendar.MONTH) + 1 < 10) {
			sb.append("0");
		}
		sb.append(cal.get(Calendar.MONTH) + 1);
		sb.append("-");
		if (cal.get(Calendar.DAY_OF_MONTH) < 10) {
			sb.append("0");
		}
		sb.append(cal.get(Calendar.DAY_OF_MONTH));
		return sb.toString();
	}

	public static Timestamp getCurrentTimestamp() {
		Timestamp ts = new Timestamp(System.currentTimeMillis());
		return ts;
	}

	public static String getDateTimeByLocale(Timestamp time, Locale locale) {
		if (time == null)
			return null;
		if (locale == null) {
			locale = Locale.CHINA;
		}
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, locale);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time.getTime());
		String result = df.format(cal.getTime());
		if (locale.getLanguage().equalsIgnoreCase(LANG_ZH)) {
			int hour = cal.get(Calendar.HOUR_OF_DAY);
			if (hour == 0) {
				result = result.replaceFirst(Locale_Changes_For_ZH[0], Locale_Changes_For_ZH[1]);
			} else if (hour == 12) {
				result = result.replaceFirst(Locale_Changes_For_ZH[2], Locale_Changes_For_ZH[3]);
			}
		}
		return result;
	}

	public static String getShortDateTimeByLocale(Date time, Locale locale) {
		if (time == null)
			return null;
		if (locale == null) {
			locale = Locale.CHINA;
		}
		DateFormat df = DateFormat.getDateInstance(DateFormat.FULL, locale);
		return df.format(time);
	}

	public static String getFullDateTimeByLocale(Date time, Locale locale) {
		if (time == null)
			return null;
		if (locale == null) {
			locale = Locale.CHINA;
		}
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.MEDIUM, locale);
		return df.format(time);
	}

	/**
	 * 根据传入时间和分钟数返回新的timestamp
	 * @param timestamp 当前timestamp
	 * @param min 分钟数 可以为负数，表示减去
	 * @return
	 */
	public static Timestamp getTimestampForAddMin(Timestamp timestamp, int min) {
		if (timestamp == null)
			return null;
		long time2 = 0;
		long time = timestamp.getTime();
		if (min < 0) {
			time2 = time - (Math.abs(min) * 60 * 1000L);
		} else {
			time2 = time + (min * 60 * 1000L);
		}
		Timestamp result = new Timestamp(time2);
		return result;
	}

	public static String[] stringToArray(String str,String regex) throws UnsupportedEncodingException{
		String[] strArray = str.split(regex);
		String[] array = new String[strArray.length];
		for (int i = 0; i < strArray.length; i++) {
			String[] paramArr = strArray[i].split("\\*");
			if (paramArr.length > 1)
				array[i] = new String(paramArr[1].getBytes("iso8859-1"), "UTF-8").trim().replaceAll("'", "");
			else
				array[i] = "";
		}
		return array;
	}

	/**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map<String,Object> convertBean(Object bean,String cols)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();

        String[] properties = cols.split(",");
        Object[] cellData = new Object[properties.length];
        for (int m = 0;m<properties.length;m++){
        	for (int i = 0; i< propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (propertyName.equals(properties[m])){
                	 if (!propertyName.equals("class")) {
                         Method readMethod = descriptor.getReadMethod();
                         Object result = readMethod.invoke(bean);
                         if (result != null) {
                         	if (result instanceof Timestamp){
                         		result = DateUtil.formatTimestampToStringByFmt((Timestamp) result, DateUtil.NORMAL_TIME_FORMAT);
                         	}
                             if (propertyName.equals("id")){
                             	 returnMap.put(propertyName, result);
                             }
                             cellData[m] = result;
                         } else {
                         	 if (propertyName.equals("id")){
                         		 returnMap.put(propertyName, "");
                             }
                         	cellData[m] = "";
                         }
                     }
                	 break;
                }
            }
        }
        returnMap.put("cell", cellData);
        return returnMap;
    }

    /**
     * 将一个 Map 对象转化为一个 JavaBean
     * @param type 要转化的类型
     * @param map 包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InstantiationException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings("rawtypes")
    public static Object convertMap(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象
        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);

                Object[] args = new Object[1];
                args[0] = value;

                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }

    /**
     * map to json String
     * @param map
     * @return
     */
    public static String mapToJsonStr(Map<String,Object> map){
    	JSONObject object = JSONObject.fromObject(map);
		return object.toString();
    }
    /**
     * BeanList convert to MapList
     * @param beans
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static List<Map<String,Object>> beanListToMapList(List<?> beans,String cols) throws IntrospectionException, IllegalAccessException, InvocationTargetException{
    	List<Map<String,Object>> cells = new ArrayList<Map<String,Object>>();
    	for (Object bean : beans){
    		Map<String,Object> cell = convertBean(bean,cols);
    		cells.add(cell);
    	}
    	return cells;
    }

    /**
     * 创建页面datagrid所需的专用格式数据
     * @param page
     * @param total
     * @param results
     * @return
     * @throws IntrospectionException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws UnsupportedEncodingException
     */
    public static String createPageInfo(String page,long total,List<?> results,String cols) throws IntrospectionException, IllegalAccessException, InvocationTargetException, UnsupportedEncodingException{
    	Map<String, Object> pageInfo = new HashMap<String, Object>();
		pageInfo.put("total", total);
		pageInfo.put("page", Integer.parseInt(page));
		pageInfo.put("rows", beanListToMapList(results,cols));
		String jsonStr = mapToJsonStr(pageInfo);
		return jsonStr;
    }
}
