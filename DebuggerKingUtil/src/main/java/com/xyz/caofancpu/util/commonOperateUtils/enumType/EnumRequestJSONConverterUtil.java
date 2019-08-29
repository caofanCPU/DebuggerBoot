package com.xyz.caofancpu.util.commonOperateUtils.enumType;

import com.xyz.caofancpu.util.commonOperateUtils.enumType.converter.StringToEnumConverterFactory;
import com.xyz.caofancpu.util.commonOperateUtils.enumType.converter.ValueToEnumConverterFactory;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * 枚举响应转换器
 *
 * @author caofanCPU
 */
public class EnumRequestJSONConverterUtil {

    public static ConverterFactory buildValueToEnumConverterFactory() {
        return new ValueToEnumConverterFactory();
    }

    public static ConverterFactory buildStringToEnumConverterFactory() {
        return new StringToEnumConverterFactory();
    }

}
