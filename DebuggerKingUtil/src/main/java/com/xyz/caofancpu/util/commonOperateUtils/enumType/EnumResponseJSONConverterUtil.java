package com.xyz.caofancpu.util.commonOperateUtils.enumType;

import com.fasterxml.jackson.databind.JsonSerializer;
import com.xyz.caofancpu.util.commonOperateUtils.enumType.converter.EnumResponseJSONConverter;

/**
 * 枚举响应转换器
 */
public class EnumResponseJSONConverterUtil {

    public static JsonSerializer build() {
        return new EnumResponseJSONConverter();
    }
}
