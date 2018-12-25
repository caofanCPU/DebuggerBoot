package com.xyz.caofancpu.util.commonOperateUtils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 * Created by caofanCPU on 2018/8/6.
 */
public class MessageConverterUtil {
    /**
     * LOG
     */
    private static final Logger logger = LoggerFactory.getLogger(MessageConverterUtil.class);
    
    /**
     * 将JSON字符串转为消息对象
     *
     * @param jsonString
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T convertJsonToMessage(String jsonString, Class<T> clazz) {
        if (StringUtils.isEmpty(jsonString) || Objects.isNull(clazz)) {
            throw new IllegalArgumentException("非法的入参");
        }
        return JSONObject.parseObject(jsonString, clazz);
    }
    
}
