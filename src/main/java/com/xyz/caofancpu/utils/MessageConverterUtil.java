package com.xyz.caofancpu.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by caofanCPU on 2018/8/6.
 */
public class MessageConverterUtil {
    
    /**
     * 将JSON字符串转为消息对象
     *
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T convertJsonToMessage(String jsonString, Class<T> clazz) {
        T result = null;
        if (StringUtils.isNotEmpty(jsonString)) {
            result = JSONObject.parseObject(jsonString, clazz);
        }
        return result;
    }
    
}
