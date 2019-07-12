package com.xyz.caofancpu.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class DataHelper {

    private static final String SET_METHOD = "set";

    public static void putDataIntoEntity(Map<String, Object> map, Object entity)
            throws SecurityException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException, ParseException {
        if (entity != null && map != null && map.size() > 0) {
            //获取传入实体类的属性Filed数组
            Field[] fieldArr = Class.forName(entity.getClass().getCanonicalName()).getDeclaredFields();
            //遍历数组
            for (Field field : fieldArr) {
                //获取属性名称
                field.setAccessible(true);
                String fieldName = field.getName();
                //判断map中是否存在对应的属性名称（注：这个方法要想使用就必须保证map中的key与实体类的属性名称一致）
                if (map.containsKey(fieldName)) {
                    //调用本类中的帮助方法来获取当前属性名对应的方法名（“set”为getMethodName方法的第二个参数）
                    String methodName = getMethodName(fieldName, SET_METHOD);
                    //获取当前key对应的值
                    Object obj = map.get(fieldName);
                    if ("class java.lang.Integer".equals(field.getGenericType().toString())) {
                        obj = Integer.valueOf(obj.toString());
                    } else if ("class java.lang.Float".equals(field.getGenericType().toString())) {
                        obj = Float.valueOf(obj.toString());
                    } else if ("class java.lang.Double".equals(field.getGenericType().toString())) {
                        obj = Double.valueOf(obj.toString());
                    } else if ("class java.lang.Boolean".equals(field.getGenericType().toString())) {
                        obj = Boolean.valueOf(obj.toString());
                    } else if ("class java.lang.Long".equals(field.getGenericType().toString())) {
                        obj = Long.valueOf(obj.toString());
                    } else if ("class java.util.Date".equals(field.getGenericType().toString())) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        obj = sdf.parse(obj.toString());
                    }

                    //根据获取的方法名称及当前field的类型获取method对象
                    Method method = entity.getClass().getDeclaredMethod(methodName, field.getType());
                    method.setAccessible(true);
                    //调用当前实体类的方法将数值set进去
                    method.invoke(entity, obj);
                }
            }
        }
    }

    /**
     * @param key        属性名
     * @param methodType 获取方法类型（set or get）
     * @return 方法名称，反射使用
     */
    public static String getMethodName(String key, String methodType) {
        String methodName = "";
        if (StringUtils.isNotEmpty(key)) {
            char[] carr = key.toCharArray();
            carr[0] -= 32;
            methodName += String.valueOf(carr);
        }
        return methodType + methodName;
    }

    /**
     * 该工具类将http请求参数，都转变为字符串，尤其是数组，变成","拼接的字符串
     *
     * @param request
     * @return
     */
    public static Map<String, Object> getParameterMap(HttpServletRequest request) {
        // 参数Map
        Map<String, String[]> properties = request.getParameterMap();
        // 返回值Map
        Map<String, Object> returnMap = new HashMap();
        Iterator entries = properties.entrySet().iterator();
        Map.Entry entry;
        String name;
        String value;
        while (entries.hasNext()) {
            entry = (Map.Entry) entries.next();
            name = (String) entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = "";
            } else {
                String[] values = (String[]) valueObj;
                value = values[0];
            }
            returnMap.put(name, value);
        }
        return returnMap;
    }


    public static void putDefaultValueToBean(Object entity)
            throws SecurityException, ClassNotFoundException, NoSuchMethodException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class<?> clazz = entity.getClass();
        Field[] fieldArr = Class.forName(clazz.getCanonicalName()).getDeclaredFields();
        String name;
        Method m;
        //遍历数组
        for (Field field : fieldArr) {
            field.setAccessible(true);
            name = field.getName(); // 获取属性的名字
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            m = entity.getClass().getMethod("get" + name);
            m.setAccessible(true);
            if ("class java.lang.String".equals(field.getGenericType().toString())) {
                String value = (String) m.invoke(entity); // 调用getter方法获取属性值
                if (value == null) {
                    m = clazz.getMethod(SET_METHOD + name, String.class);
                    m.invoke(entity, "");
                }

            } else if ("class java.lang.Integer".equals(field.getGenericType().toString())) {
                Integer value = (Integer) m.invoke(entity); // 调用getter方法获取属性值
                if (value == null) {
                    m = clazz.getMethod(SET_METHOD + name, Integer.class);
                    m.invoke(entity, 0);
                }


            } else if ("class java.lang.Long".equals(field.getGenericType().toString())) {
                Integer value = (Integer) m.invoke(entity); // 调用getter方法获取属性值
                if (value == null) {
                    m = clazz.getMethod(SET_METHOD + name, Integer.class);
                    m.invoke(entity, 0L);
                }

            } else if ("class java.lang.Float".equals(field.getGenericType().toString())) {
                Float value = (Float) m.invoke(entity); // 调用getter方法获取属性值
                if (value == null) {
                    m = clazz.getMethod(SET_METHOD + name, Float.class);
                    m.invoke(entity, 0.0F);
                }

            } else if ("class java.lang.Double".equals(field.getGenericType().toString())) {
                Double value = (Double) m.invoke(entity); // 调用getter方法获取属性值
                if (value == null) {
                    m = clazz.getMethod(SET_METHOD + name, Double.class);
                    m.invoke(entity, 0.0D);
                }
            } else if ("class java.lang.Boolean".equals(field.getGenericType().toString())) {
                Boolean value = (Boolean) m.invoke(entity);
                if (value == null) {
                    m = clazz.getMethod(SET_METHOD + name, Boolean.class);
                    m.invoke(entity, false);
                }
            } else if ("class java.util.Date".equals(field.getGenericType().toString())) {
                Date value = (Date) m.invoke(entity);
                if (value == null) {
                    m = clazz.getMethod(SET_METHOD + name, Date.class);
                    m.invoke(entity, new Date());
                }
            }

        }

    }
}
