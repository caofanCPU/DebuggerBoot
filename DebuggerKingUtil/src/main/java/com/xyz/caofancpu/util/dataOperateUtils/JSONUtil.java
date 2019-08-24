package com.xyz.caofancpu.util.dataOperateUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xyz.caofancpu.util.result.GlobalErrorInfoRuntimeException;
import com.xyz.caofancpu.util.streamOperateUtils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by caofanCPU on 2018/8/6.
 */
@Slf4j
public class JSONUtil {

    public static String toJSONStringWithDateFormat(Object data) {
        return JSONObject.toJSONStringWithDateFormat(data, DateUtil.DATETIME_FORMAT_SIMPLE);
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
    public static <T extends Serializable> String serializeJSON(T object) {
        return JSONObject.toJSONString(object, SerializerFeature.WriteClassName);
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
     * 反序列化对象
     *
     * @param jsonStr
     * @param clazz
     * @return
     */
    public static Object deserializeJSON(String jsonStr, Class<? extends Serializable> clazz) {
        return JSONObject.parseObject(jsonStr, clazz);
    }

    /**
     * 利用序列化深拷贝复杂对象
     *
     * @param t
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deepCloneBySerialization(T t) {
        return (T) deserializeJSON(serializeJSON(t), t.getClass());
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
     * Object转List, 用Bean接收
     *
     * @param jsonArray
     * @return
     */
    public static <T> List<T> convertToList(Object jsonArray, Class<T> clazz) {
        JSONArray array = parseJSONArray(jsonArray);
        return CollectionUtil.transToList(array, item -> JSONObject.parseObject(JSONObject.toJSONString(item), clazz));
    }

    /**
     * JSONArray转List, 用Map<String, Object>接收
     *
     * @param jsonArray
     * @return
     * @throws GlobalErrorInfoRuntimeException
     */
    public static List<Map<String, Object>> convertToList(Object jsonArray)
            throws GlobalErrorInfoRuntimeException {
        if (Objects.isNull(jsonArray)) {
            log.error("JSONArray转List<Map>, 源数据不能为空!");
            throw new GlobalErrorInfoRuntimeException("源数据不能为空!");
        }
        JSONArray array = parseJSONArray(jsonArray);
        return CollectionUtil.transToList(array, item -> JSONObject.parseObject(JSONObject.toJSONString(item)));
    }

    /**
     * List转JSONArray
     *
     * @param sourceList
     * @return
     * @throws GlobalErrorInfoRuntimeException
     */
    public static JSONArray convertToJSONArray(List<?> sourceList)
            throws GlobalErrorInfoRuntimeException {
        if (CollectionUtil.isEmpty(sourceList)) {
            log.error("List转JSONArray, 源数据不能为空!");
            throw new GlobalErrorInfoRuntimeException("源数据不能为空!");
        }
        JSONArray resultArray = new JSONArray();
        sourceList.forEach(item -> {
            if (Objects.nonNull(item)) {
                resultArray.add(item);
            }
        });
        return resultArray;
    }

    /**
     * 复制源对象属性到目标类
     * 根据属性名称匹配, 支持map转bean, bean之间互转, bean中嵌套集合转化
     *
     * @param sourceObject
     * @param clazz
     * @param <T>
     * @return
     * @throws GlobalErrorInfoRuntimeException
     */
    public static <T> T copyProperties(Object sourceObject, Class<T> clazz)
            throws GlobalErrorInfoRuntimeException {
        if (Objects.isNull(sourceObject)) {
            log.error("属性复制, 源数据不能为空!");
            throw new GlobalErrorInfoRuntimeException("源数据不能为空!");
        }
        if (Objects.isNull(clazz)) {
            log.error("属性复制, 目标类对象不能为空!");
            throw new GlobalErrorInfoRuntimeException("目标类对象不能为空!");
        }
        return JSONObject.parseObject(JSONObject.toJSONString(sourceObject), clazz);
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
    private static JSONArray parseJSONArray(Object jsonArray) {
        String jsonString = serializeJSON(jsonArray);
        return JSONObject.parseArray(jsonString);
    }
}
