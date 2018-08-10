package com.xyz.caofancpu.utils;

import com.xyz.caofancpu.model.FixedQuery;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caofanCPU on 2018/8/10.
 */
public class BeanConvertUtil {
    
    /**
     * 将Map中的数据传入Bean中
     * 约定：map.key === bean.property
     *
     * @param sourceMap
     * @param targetObj
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static void transferToBean(Map<String, Object> sourceMap, Object targetObj)
            throws InvocationTargetException, IllegalAccessException {
        BeanUtils.populate(targetObj, sourceMap);
        /**
         * copyProperties包含populate的功能，但是copyProperties适用于从httpRequest中转换数据，故而平常不推荐使用
         */
//        BeanUtils.copyProperties(targetObj, sourceMap);
    }
    
    
    /**
     * 将对象中的数据传入map中
     *
     * @param sourceObj
     * @param targetMap
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static void transferToMap(Object sourceObj, Map<String, Object> targetMap)
            throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Map<String, String> sourceMap = BeanUtils.describe(sourceObj);
        sourceMap.remove("class");
        sourceMap.entrySet().stream().forEach(item -> {
            if (!"null".equals(item.getValue())) {
                targetMap.put(item.getKey(), item.getValue());
            }
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
            // 过滤null值
            if (map.get(field.getName()) != null) {
                field.setAccessible(true);
                field.set(obj, map.get(field.getName()));
            }
        }
        return obj;
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
    
    public static void main(String[] args) {
        testDescripe();
        
    }
    
    public static void testDescripe() {
        FixedQuery fixedQuery = new FixedQuery(10, "渣渣");
        Map<String, String> map = null;
        try {
            map = BeanUtils.describe(fixedQuery);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        if (map != null) {
            map.entrySet().stream().forEach(item -> System.out.println("k=" + item.getKey() + ", v=" + item.getValue()));
        }
    }
    
}
