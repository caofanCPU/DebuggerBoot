package com.xyz.caofancpu.util.dataOperateUtils;


import com.xyz.caofancpu.util.result.GlobalErrorInfoRuntimeException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * Created by caofanCPU on 2018/8/10.
 */
public class BeanConvertUtil {
    /**
     * LOG
     */
    private static final Logger logger = LoggerFactory.getLogger(BeanConvertUtil.class);
    
    /**
     * 将Map中的数据替换Bean中属性
     * 约定：map.key === bean.property
     *
     * @param sourceMap
     * @param targetObj
     * @throws GlobalErrorInfoRuntimeException
     */
    public static void replaceBeanProperty(Map<String, Object> sourceMap, Object targetObj)
            throws GlobalErrorInfoRuntimeException {
        if (!validate(sourceMap, targetObj)) {
            return;
        }
        ConvertUtils.register(loadDateConverter(), Date.class);
        try {
            BeanUtils.populate(targetObj, sourceMap);
        } catch (Exception e) {
            logger.error("Map替换Bean, 对象属性复制失败: \n", e);
            throw new GlobalErrorInfoRuntimeException("对象属性复制失败!");
        }
        /**
         * copyProperties包含populate的功能，但是copyProperties适用于从httpRequest中转换数据，
         * 故而平常不推荐使用
         * BeanUtils.copyProperties(targetObj, sourceMap);
         */
    }
    
    /**
     * copyProperties包含populate的功能，但是copyProperties适用于从httpRequest中转换数据，
     * 故而平常不推荐使用
     * @param sourceObj
     * @param clazz
     * @param <T>
     * @return
     * @throws GlobalErrorInfoRuntimeException
     */
    public static <T> T copyProperties(Object sourceObj, Class<T> clazz)
            throws GlobalErrorInfoRuntimeException {
        T targetObj;
        try {
            targetObj = clazz.newInstance();
        } catch (Exception e) {
            logger.error("对象属性复制失败: \n", e);
            throw new GlobalErrorInfoRuntimeException("对象属性复制失败, 原因: 目标类不存在");
        }
        ConvertUtils.register(loadDateConverter(), Date.class);
        try {
            BeanUtils.copyProperties(targetObj, sourceObj);
        } catch (Exception e) {
            logger.error("对象属性复制失败: \n", e);
            throw new GlobalErrorInfoRuntimeException("对象属性复制失败!");
        }
        return targetObj;
    }
    
    /**
     * 将Bean中的属性替换Map中的数据
     * 约定：map.key === bean.property
     *
     * @param sourceObj
     * @param targetMap
     * @throws GlobalErrorInfoRuntimeException
     */
    public static void replaceMapProperty(Object sourceObj, Map<String, Object> targetMap)
            throws GlobalErrorInfoRuntimeException {
        if (!validate(targetMap, sourceObj)) {
            return;
        }
        final String classFeatureKey = "class";
        final String nullFeatureKey = "null";
        Map<String, String> sourceMap;
        try {
            sourceMap = BeanUtils.describe(sourceObj);
        } catch (Exception e) {
            logger.error("Bean替换Map, 对象转Map失败: \n", e);
            throw new GlobalErrorInfoRuntimeException("对象转Map失败!");
        }
        sourceMap.remove(classFeatureKey);
        sourceMap.entrySet().stream()
                .forEach(item -> {
                    if (!nullFeatureKey.equals(item.getValue())) {
                        targetMap.put(item.getKey(), item.getValue());
                    }
        });
    }

//    /**
//     * 将Map中的数据导入Bean中，根据字段映射完成
//     * 例子：sourceDataMap["shopName":"hello"]
//     * propertyMap["shopName":"organizationName"]
//     * targetObj[organizationName=null]
//     * 结果：targetObj[organizationName="hello"]
//     *
//     * @param sourceDataMap
//     * @param propertyMap
//     * @param targetObj
//     * @throws GlobalErrorInfoRuntimeException
//     */
//    public static void transferToBean(Map<String, Object> sourceDataMap, Map<String, Object> propertyMap, Object targetObj)
//            throws GlobalErrorInfoRuntimeException {
//        if (!validate(sourceDataMap, targetObj) || !validate(propertyMap, targetObj)) {
//            return;
//        }
//        Set<Map.Entry<String, Object>> propertyEntrySet = propertyMap.entrySet();
//        propertyEntrySet.stream()
//                .forEach(propertyEntry -> {
//                    String mapKey = propertyEntry.getKey();
//                    String fieldName = (String) propertyEntry.getValue();
//                    // 根据Bean字段的属性类型，进行强制转换
//                    try {
//                        Field presentField = targetObj.getClass().getDeclaredField(fieldName);
//                        String fieldTypeName = presentField.getGenericType().getTypeName().toString();
//                        sourceDataMap.put(mapKey, convertObject(fieldTypeName, sourceDataMap.get(mapKey)));
//                    } catch (Exception e) {
//                        logger.error("Map转Bean失败: \n", e);
//                        throw new GlobalErrorInfoRuntimeException("Map转Bean失败!");
//                    }
//                    ReflectionUtil.setFieldValue(targetObj, fieldName, sourceDataMap.get(mapKey));
//        });
//    }

//    /**
//     * 将map元素生成指定类型的Bean
//     * 过滤null值
//     *
//     * @param map
//     * @param beanClass
//     * @return
//     * @throws GlobalErrorInfoRuntimeException
//     */
//    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws GlobalErrorInfoRuntimeException {
//        if (Objects.isNull(map)) {
//            logger.error("Map转Bean, 入参源数据不能为空!");
//            throw new GlobalErrorInfoRuntimeException("源数据不能为空!");
//        }
//        Object obj;
//        try {
//            obj = beanClass.newInstance();
//        } catch (Exception e) {
//            logger.error("目标类文件不存在: \n", e);
//            throw new GlobalErrorInfoRuntimeException("目标类文件不存在!");
//        }
//        Field[] fields = obj.getClass().getDeclaredFields();
//        for (Field field : fields) {
//            int mod = field.getModifiers();
//            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
//                continue;
//            }
//            Object present = map.get(field.getName());
//            // 过滤null值
//            if (Objects.nonNull(present)) {
//                // 获取对象的属性类型，并强转为其类型
//                String fieldTypeName = field.getGenericType().getTypeName();
//                map.put(field.getName(), convertObject(fieldTypeName, present));
//                field.setAccessible(true);
//                try {
//                    field.set(obj, map.get(field.getName()));
//                } catch (Exception e) {
//                    logger.error("Map转Bean失败, 原因: Bean字段赋值失败", e);
//                    throw new GlobalErrorInfoRuntimeException("Map转Bean失败!");
//                }
//            }
//        }
//        return obj;
//    }


//    /**
//     * 对目标对象进行强制类型转换
//     *
//     * @param fieldTypeName
//     * @param targetObj
//     * @return
//     * @throws GlobalErrorInfoRuntimeException
//     */
//    public static Object convertObject(final String fieldTypeName, Object targetObj) throws GlobalErrorInfoRuntimeException {
//        switch (fieldTypeName) {
//            case "java.lang.Integer":
//                return Integer.valueOf(targetObj.toString());
//            case "java.lang.Float":
//                return Float.valueOf(targetObj.toString());
//            case "java.lang.Double":
//                return Double.valueOf(targetObj.toString());
//            case "java.lang.Boolean":
//                return Boolean.valueOf(targetObj.toString());
//            case "java.lang.Long":
//                return Long.valueOf(targetObj.toString());
//            case "java.math.BigDecimal":
//                return new BigDecimal(targetObj.toString());
//            case "java.util.Date":
//                try {
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    return sdf.parse(targetObj.toString());
//                } catch (ParseException e) {
//                    logger.error("日期格式转换失败, 目标格式:yyyy-MM-dd HH:mm:ss\n", e);
//                    throw new GlobalErrorInfoRuntimeException("日期格式转换失败!");
//                }
//            default:
//                return targetObj;
//        }
//    }
    
    /**
     * 非空检查
     *
     * @param map
     * @param obj
     * @return
     */
    public static boolean validate(Map<String, Object> map, Object obj) {
        if (Objects.isNull(map) || map.isEmpty() || Objects.isNull(obj)) {
            return false;
        }
        return true;
    }
    
    /**
     * 获取时间转换器,支持String格式时间转为java.util.Date类型
     *
     * @return
     */
    public static DateConverter loadDateConverter() {
        DateConverter dateConverter = new DateConverter(null);
        dateConverter.setPatterns(new String[]{
                DateUtil.FORMAT_SIMPLE_DETAIL_PRECISE,
                DateUtil.FORMAT_SIMPLE_DETAIL,
                DateUtil.FORMAT_SIMPLE,
                DateUtil.FORMAT_SIMPLE_CN
        });
        return dateConverter;
    }
    
    /**
     * 对象转String
     *
     * @param source
     * @return
     */
    public static String convertToString(Object source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return source.toString();
    }
    
    /**
     * 转换为时间对象Date
     *
     * @param source
     * @return
     * @throws GlobalErrorInfoRuntimeException
     */
    public static Date convertToDate(Object source)
            throws GlobalErrorInfoRuntimeException {
        if (Objects.isNull(source)) {
            return null;
        }
        if (source instanceof Long) {
            return new Date(convertToLong(source));
        }
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.FORMAT_SIMPLE_DETAIL);
        try {
            return sdf.parse(source.toString());
        } catch (Exception e) {
            logger.error("日期格式转换失败, 目标格式:yyyy-MM-dd HH:mm:ss\n", e);
            throw new GlobalErrorInfoRuntimeException("日期格式转换失败!");
        }
    }
    
    /**
     * 对象转Integer
     *
     * @param source
     * @return
     */
    public static Integer convertToInteger(Object source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return Integer.parseInt(source.toString());
    }
    
    /**
     * 对象转Long
     *
     * @param source
     * @return
     */
    public static Long convertToLong(Object source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return Long.parseLong(source.toString());
    }
    
    /**
     * 对象转BigDecimal, 默认采用四舍五入保留2位小数
     *
     * @param source
     * @param newScale
     * @param roundingMode
     * @return
     */
    public static BigDecimal convertToBigDecimal(Object source, int newScale, int roundingMode) {
        if (Objects.isNull(source)) {
            return null;
        }
        if (newScale <= 0) {
            newScale = 2;
        }
        if (roundingMode <= 0) {
            roundingMode = BigDecimal.ROUND_HALF_UP;
        }
        return new BigDecimal(source.toString()).setScale(newScale, roundingMode);
    }
    
    /**
     * 对象转BigDecimal, 默认采用四舍五入保留2位小数
     *
     * @param source
     * @return
     */
    public static BigDecimal convertToDefaultBigDecimal(Object source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return convertToBigDecimal(source, 2, BigDecimal.ROUND_HALF_UP);
    }
    
}
