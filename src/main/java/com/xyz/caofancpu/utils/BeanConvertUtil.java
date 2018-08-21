package com.xyz.caofancpu.utils;


import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by caofanCPU on 2018/8/10.
 */
public class BeanConvertUtil {
    
    /**
     * 将Map中的数据替换Bean中属性
     * 约定：map.key === bean.property
     *
     * @param sourceMap
     * @param targetObj
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void replaceBeanProperty(Map<String, Object> sourceMap, Object targetObj)
            throws InvocationTargetException, IllegalAccessException {
        if (!validate(sourceMap, targetObj)) {
        
        }
        ConvertUtils.register(loadDateConverter(), Date.class);
        BeanUtils.populate(targetObj, sourceMap);
        /**
         * copyProperties包含populate的功能，但是copyProperties适用于从httpRequest中转换数据，
         * 故而平常不推荐使用
         */
//        BeanUtils.copyProperties(targetObj, sourceMap);
    }
    
    
    /**
     * 将Bean中的属性替换Map中的数据
     * 约定：map.key === bean.property
     *
     * @param sourceObj
     * @param targetMap
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static void replaceMapProperty(Object sourceObj, Map<String, Object> targetMap)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        if (!validate(targetMap, sourceObj)) {
            return;
        }
        Map<String, String> sourceMap = BeanUtils.describe(sourceObj);
        sourceMap.remove("class");
        sourceMap.entrySet().stream().forEach(item -> {
            if (!"null".equals(item.getValue())) {
                targetMap.put(item.getKey(), item.getValue());
            }
        });
    }
    
    /**
     * 将Map中的数据导入Bean中，根据字段映射完成
     * 例子：sourceDataMap["shopName":"hello"]
     * propertyMap["shopName":"organizationName"]
     * targetObj[organizationName=null]
     * 结果：targetObj[organizationName="hello"]
     *
     * @param sourceDataMap
     * @param propertyMap
     * @param targetObj
     */
    public static void transferToBean(Map<String, Object> sourceDataMap, Map<String, Object> propertyMap, Object targetObj) {
        if (!validate(sourceDataMap, targetObj) || !validate(propertyMap, targetObj)) {
            return;
        }
        Set<Map.Entry<String, Object>> propertyEntrySet = propertyMap.entrySet();
        propertyEntrySet.stream().forEach(propertyEntry -> {
            String mapKey = propertyEntry.getKey();
            String fieldName = (String) propertyEntry.getValue();
            // 根据Bean字段的属性类型，进行强制转换
            try {
                Field presentField = targetObj.getClass().getDeclaredField(fieldName);
                String fieldTypeName = presentField.getGenericType().getTypeName().toString();
                sourceDataMap.put(mapKey, convertObject(fieldTypeName, sourceDataMap.get(mapKey)));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            ReflectionUtil.setFieldValue(targetObj, fieldName, sourceDataMap.get(mapKey));
        });
    }
    
    /**
     * 将map元素生成指定类型的Bean
     * 过滤null值
     *
     * @param map
     * @param beanClass
     * @return
     * @throws Exception
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass)
            throws Exception {
        if (map == null) {
            return null;
        }
        Object obj = beanClass.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            Object present = map.get(field.getName());
            // 过滤null值
            if (present != null) {
                // 获取对象的属性类型，并强转为其类型
                String fieldTypeName = field.getGenericType().getTypeName().toString();
                map.put(field.getName(), convertObject(fieldTypeName, present));
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
        }
        return obj;
    }
    
    
    /**
     * 对目标对象进行强制类型转换
     *
     * @param fieldTypeName
     * @param targetObj
     * @return
     */
    public static Object convertObject(final String fieldTypeName, Object targetObj) {
        switch (fieldTypeName) {
            case "java.lang.Integer":
                return Integer.valueOf(targetObj.toString());
            case "java.lang.Float":
                return Float.valueOf(targetObj.toString());
            case "java.lang.Double":
                return Double.valueOf(targetObj.toString());
            case "java.lang.Boolean":
                return Boolean.valueOf(targetObj.toString());
            case "java.lang.Long":
                return Long.valueOf(targetObj.toString());
            case "java.math.BigDecimal":
                return new BigDecimal(targetObj.toString());
            case "java.util.Date":
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    return sdf.parse(targetObj.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return targetObj;
            default:
                return targetObj;
        }
    }
    
    /**
     * 将Bean中元素整合为Map<字段名, 字段值>
     * 过滤null值
     *
     * @param obj
     * @return
     * @throws Exception
     */
    public static Map<String, Object> objectToMap(Object obj)
            throws Exception {
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            // 过滤null值
            if (field.get(obj) != null) {
                map.put(field.getName(), field.get(obj));
            }
        }
        return map;
    }
    
    /**
     * 非空检查
     *
     * @param map
     * @param obj
     * @return
     */
    public static boolean validate(Map<String, Object> map, Object obj) {
        if (map == null || map.isEmpty() || obj == null) {
            return false;
        }
        return true;
    }
    
    /**
     * 对象为List<map></map>结构, 转为指定实体bean
     *
     * @param sourceData
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseTargetListObj(Object sourceData, Class<T> clazz) {
        if (sourceData == null || clazz == null || !(sourceData instanceof List)) {
            return null;
        }
        List sourceList = (List) sourceData;
        List<T> resultList = new ArrayList<>(sourceList.size());
        sourceList.stream().filter(Objects::nonNull).forEach(item -> {
            T resultObj = parseTargetObj(item, clazz);
            resultList.add(resultObj);
        });
        return resultList;
    }
    
    /**
     * 对象为map结构, 转为指定实体bean
     *
     * @param sourceData
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T parseTargetObj(Object sourceData, Class<T> clazz) {
        if (sourceData == null || clazz == null || !(sourceData instanceof Map)) {
            return null;
        }
        try {
            ConvertUtils.register(loadDateConverter(), Date.class);
            T target = clazz.newInstance();
            BeanUtils.populate(target, (Map<String, Object>) sourceData);
            return target;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 获取时间转换器,支持String格式时间转为java.util.Date类型
     *
     * @return
     */
    public static DateConverter loadDateConverter() {
        DateConverter dateConverter = new DateConverter(null);
        dateConverter.setPatterns(new String[]{DateUtil.FORMAT_ALL, DateUtil.FORMAT_SIMPLE, DateUtil.FORMAT_SIMPLE_CN});
        return dateConverter;
    }
    
    public static void main(String[] args) {
        testDescribe();
        
    }
    
    public static void testDescribe() {
    
    }
    
}
