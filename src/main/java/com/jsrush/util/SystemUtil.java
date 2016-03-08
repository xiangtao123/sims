package com.jsrush.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemUtil {

	private static final Logger log = LoggerFactory.getLogger(SystemUtil.class);
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
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map<String,Object> convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        Map cellMap = new HashMap();
    	for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
        	if (!propertyName.equals("class")) {
                 Method readMethod = descriptor.getReadMethod();
                 Object result = readMethod.invoke(bean);
                 if (result != null) {
                 	if (result instanceof Timestamp){
                 		result = DateUtil.formatTimestampToStringByFmt((Timestamp) result, DateUtil.NORMAL_SQL_DATE_FORMAT);
                 	}
                    if (propertyName.equals("id")){
                     	returnMap.put(propertyName, result);
                    }
                    cellMap.put(propertyName, result);
                 } else {
                 	 if (propertyName.equals("id")){
                 		 returnMap.put(propertyName, "");
                     }
                 	 cellMap.put(propertyName, "");
                 }
            }
        }
        returnMap.put("cell", cellMap);
        return cellMap;
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
    public static List<Map<String,Object>> beanListToMapList(List<?> beans) throws IntrospectionException, IllegalAccessException, InvocationTargetException{
    	List<Map<String,Object>> cells = new ArrayList<Map<String,Object>>();
    	for (Object bean : beans){
    		Map<String,Object> cell = convertBean(bean);
    		cells.add(cell);
    	}
    	return cells;
    }

    /**
     * 创建页面datagrid所需的专用格式数据
     * 适应场景：单表无外键关系
     * @param pageNo		当前页数
     * @param pageSize	每页的大小
     * @param total		总记录数
     * @param results		数据库查询出的列表
     * @return json String
     */
    public static String createPageInfo(int pageNo, int pageSize, long total, List<?> results) {
    	Map<String, Object> pageInfo = new HashMap<String, Object>();
		pageInfo.put("total",  total%pageSize > 0 ? total/pageSize +1 : total/pageSize);
		pageInfo.put("page", pageNo);
		pageInfo.put("records", total);
		try {
			pageInfo.put("rows",  beanListToMapList(results) );
			String jsonStr = mapToJsonStr(pageInfo);
			System.out.println(jsonStr);
			return jsonStr;
		}  catch (IllegalAccessException e) {
			e.printStackTrace();
			log.error(LogUtil.stackTraceToString(e));
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			log.error(LogUtil.stackTraceToString(e));
		} catch (IntrospectionException e) {
			e.printStackTrace();
			log.error(LogUtil.stackTraceToString(e));
		}
		return null;
    }

  /**
   * 创建页面datagrid所需的专用格式数据:利用 Spring mvc jackson convert Map to Json
   * 适应场景：有外键关系
   * @param pageNo		当前页数
   * @param pageSize	每页的大小
   * @param total		总记录数
   * @param results		数据库查询出的列表
   * @return Map<String, Object>
   */
    public static Map<String, Object> createPageInfoMap(int pageNo, int pageSize, long total, List<?> results) {
    	Map<String, Object> pageInfo = new HashMap<String, Object>();
//		pageInfo.put("total",  total%pageSize > 0 ? total/pageSize +1 : total/pageSize);
//		pageInfo.put("page", pageNo);
//		pageInfo.put("records", total);
		pageInfo.put("rows",  results );
		pageInfo.put("total", total);
		return pageInfo;
    }

    /**
     * convert string to json object.
     * @param params
     * @return
     */
    public static JSONObject fromObject(String params){
    	return StringHelper.isEmpty(params)?null:JSONObject.fromObject(params);
    }

    /**
     * convert Object to json Object.
     * @param obj
     * @return
     */
    public static JSONObject fromObject(Object obj){
    	return obj == null ? null:JSONObject.fromObject(obj);
    }

    /**
     * convert json string to bean object.
     * @param params
     * @return
     */
    public static Object fromObject(String params, Class<?> beanClass){
    	return StringHelper.isEmpty(params) ? null: JSONObject.toBean(JSONObject.fromObject(params), beanClass);
    }

	public static int firstNo(int pageNo, int pageSize) {
		return pageNo < 2 ? 0 : (pageNo-1) * pageSize;
	}


	public static String readProjectPath(){
		File file = new File("");
		try {
			return file.getCanonicalPath();
		} catch (IOException e) {
			System.err.println("项目路径获取失败");
			e.printStackTrace();
			return null;
		}
	}

	public static String readProjectWebRootPath(){
		return readProjectPath() + File.separator+"src"+ File.separator+"main"+ File.separator+"webapp";
	}

	public static String setDefaultSubjectId(Long defaultSubjectLong, String params){
		if (StringHelper.isEmpty(params)) {
			return null;
		}
		if (defaultSubjectLong == null) {
			log.info("未设定默认会议主题");
			return null;
		}
		JSONObject paramsJsonObj = JSONObject.fromObject(params);
		paramsJsonObj.put("subjectId", defaultSubjectLong);
		return paramsJsonObj.toString();
	}
}
