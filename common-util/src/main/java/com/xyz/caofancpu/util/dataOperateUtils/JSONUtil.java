package com.xyz.caofancpu.util.dataOperateUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xyz.caofancpu.util.result.GlobalErrorInfoRuntimeException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by caofanCPU on 2018/8/6.
 */
public class JSONUtil {
    
    /**
     * LOG
     */
    private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);
    
    /**
     * 推荐使用
     * ResultBody.getData()为数组时, 转化为List<Map<String, Object>>
     * @param source
     * @return
     */
    public static List<Map<String, Object>> parseList(Object source) {
        List<Map> tempList = new ArrayList();
        if (source instanceof ArrayList) {
            tempList = (ArrayList) source;
        }
        List<Map<String, Object>> resultList = new ArrayList<>();
        tempList.forEach((item -> resultList.add(item)));
        return resultList;
    }
    
    public static String formatStandardJSON(String jsonStr) {
        String start = "    ";
        // 用户标记层级
        int level = 0;
        StringBuilder jsonResultStr = new StringBuilder();
        // 循环遍历每一个字符
        for (int i = 0; i < jsonStr.length(); i++) {
            // 获取当前字符
            char piece = jsonStr.charAt(i);
            // 如果上一个字符是断行，则在本行开始按照level数值添加标记符，排除第一行
            if (i != 0 && '\n' == jsonResultStr.charAt(jsonResultStr.length() - 1)) {
                for (int k = 0; k < level; k++) {
                    jsonResultStr.append(start);
                }
            }
            switch (piece) {
                case '{':
                case '[':
                    // 如果字符是{或者[，则断行，level加1
                    jsonResultStr.append(piece + "\n");
                    level++;
                    break;
                case ',':
                    // 如果是","，则断行
                    jsonResultStr.append(piece + "\n");
                    break;
                case '}':
                case ']':
                    // 如果是"}"或者"]"，则断行，level减1
                    jsonResultStr.append("\n");
                    level--;
                    for (int k = 0; k < level; k++) {
                        jsonResultStr.append(start);
                    }
                    jsonResultStr.append(piece);
                    break;
                default:
                    jsonResultStr.append(piece);
                    break;
            }
        }
        return jsonResultStr.toString().replaceAll("\n\n", "\n");
    }
    
    /**
     * 对象序列化为JSONString
     *
     * @param object
     * @return
     */
    public static String serializeJSON(Object object) {
        return JSONObject.toJSONString(object, SerializerFeature.WriteClassName);
    }
    
    /**
     * 对象序列化为标准格式的JSONString
     *
     * @param object
     * @return
     */
    public static String serializeStandardJSON(Object object) {
        String jsonString = JSONObject.toJSONString(object, SerializerFeature.WriteClassName);
        return formatStandardJSON(jsonString);
    }
    
    /**
     * 对象解析为JSONArray
     * 可能出现:  com.alibaba.fastjson.JSONException: autoType is not support ...
     * 解决办法: 配置JVM启动参数: -Dfastjson.parser.autoTypeAccept=序列化对象包根目录1. , 序列化对象包根目录2. 等
     * 说明: Spring Boot / Spring + Tomcat 启动默认支持 自动类型解析的
     *
     * @param jsonArray
     * @return
     */
    public static JSONArray parseJSONArray(Object jsonArray) {
        String jsonString = serializeJSON(jsonArray);
        return JSONObject.parseArray(jsonString);
    }
    
    /**
     * JSONArray转List, 用Bean接收
     *
     * @param jsonArray
     * @return
     * @throws GlobalErrorInfoRuntimeException
     */
    public static <T> List<T> arrayShiftToBean(Object jsonArray, Class<T> clazz)
            throws GlobalErrorInfoRuntimeException {
        if (Objects.isNull(jsonArray) || Objects.isNull(clazz)) {
            throw new NullPointerException();
        }
        JSONArray array = parseJSONArray(jsonArray);
        List<T> resultList = new ArrayList<>();
        array.stream()
                .filter(Objects::nonNull)
                .forEach(item -> {
                    try {
                        T result = BeanConvertUtil.copyProperties(item, clazz);
                        resultList.add(result);
                    } catch (Exception e) {
                        logger.info("JSONArray转List<Bean>失败!\n", e);
                        throw new GlobalErrorInfoRuntimeException("对象转换失败!");
                    }
                });
        return resultList;
    }
    
    /**
     * JSONArray转List, 用Map<String, Object>接收
     *
     * @param jsonArray
     * @return
     * @throws GlobalErrorInfoRuntimeException
     */
    public static List<Map<String, Object>> arrayShiftToMap(Object jsonArray)
            throws GlobalErrorInfoRuntimeException {
        if (Objects.isNull(jsonArray)) {
            logger.error("JSONArray转List<Map>, 源数据不能为空!");
            throw new GlobalErrorInfoRuntimeException("源数据不能为空!");
        }
        JSONArray array = parseJSONArray(jsonArray);
        List<Map<String, Object>> resultList = new ArrayList<>();
        array.stream()
                .filter(Objects::nonNull)
                .forEach(item -> resultList.add(JSONObject.parseObject(JSONObject.toJSONString(item))));
        return resultList;
    }
    
    /**
     * List转JSONArray
     *
     * @param sourceList
     * @return
     * @throws GlobalErrorInfoRuntimeException
     */
    public static JSONArray shiftToJSONArray(List<?> sourceList)
            throws GlobalErrorInfoRuntimeException {
        if (CollectionUtils.isEmpty(sourceList)) {
            logger.error("List转JSONArray, 源数据不能为空!");
            throw new GlobalErrorInfoRuntimeException("源数据不能为空!");
        }
        JSONArray resultArray = new JSONArray();
        sourceList.stream()
                .filter(Objects::nonNull)
                .forEach(item -> resultArray.add(item));
        return resultArray;
    }
    
    /**
     * Map<String, Object> 转 JSONObject
     *
     * @param sourceMap
     * @return
     * @throws GlobalErrorInfoRuntimeException
     */
    public static JSONObject mapShiftToJSON(Map<String, Object> sourceMap)
            throws GlobalErrorInfoRuntimeException {
        if (Objects.isNull(sourceMap) || 0 == sourceMap.size()) {
            logger.error("Map转JSONObject, 源数据数据不能为空!");
            throw new GlobalErrorInfoRuntimeException("源数据数据不能为空!");
        }
        return JSONObject.parseObject(JSONObject.toJSONString(sourceMap));
    }
    
    /**
     * JSONObject 转 Map<String, Object>
     * JSONObject是Map的子类
     * @param jsonObject
     * @return
     * @throws GlobalErrorInfoRuntimeException
     * @since 0.0.1
     */
    @Deprecated
    public static Map<String, Object> JSONShiftToMap(JSONObject jsonObject)
            throws GlobalErrorInfoRuntimeException {
        if (Objects.isNull(jsonObject)) {
            logger.error("JSONObject转Map, 源数据数据不能为空!");
            throw new GlobalErrorInfoRuntimeException("源数据数据不能为空!");
        }
        return jsonObject;
    }
    
    /**
     * Map转Bean
     *
     * @param sourceObject
     * @param clazz
     * @param <T>
     * @return
     * @throws GlobalErrorInfoRuntimeException
     */
    public static <T> T mapToBean(Object sourceObject, Class<T> clazz)
            throws GlobalErrorInfoRuntimeException {
        if (Objects.isNull(sourceObject) || Objects.isNull(clazz)) {
            logger.error("Map转Bean, 源数据数据不能为空!");
            throw new GlobalErrorInfoRuntimeException("源数据数据不能为空!");
        }
        return JSONObject.parseObject(JSONObject.toJSONString(sourceObject), clazz);
    }
    
}
