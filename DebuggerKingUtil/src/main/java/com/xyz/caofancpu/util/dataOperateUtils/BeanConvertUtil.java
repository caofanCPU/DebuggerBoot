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
     *
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
                DateUtil.DATETIME_FORMAT_SIMPLE_DETAIL_PRECISE,
                DateUtil.DATETIME_FORMAT_SIMPLE_DETAIL,
                DateUtil.DATE_FORMAT_SIMPLE,
                DateUtil.DATE_FORMAT_SIMPLE_CN
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
        SimpleDateFormat sdf = new SimpleDateFormat(DateUtil.DATETIME_FORMAT_SIMPLE_DETAIL);
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
