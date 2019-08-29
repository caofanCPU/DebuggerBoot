package com.xyz.caofancpu.util.commonOperateUtils.enumType;

import com.xyz.caofancpu.util.commonOperateUtils.enumType.converter.EnumRequestJSONConverter;
import com.xyz.caofancpu.util.commonOperateUtils.enumType.converter.StringToEnumConverterFactory;
import com.xyz.caofancpu.util.commonOperateUtils.enumType.converter.ValueToEnumConverterFactory;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * 枚举响应转换器
 *
 * @author caofanCPU
 */
public class EnumRequestJSONConverterUtil {

    @Deprecated
    public static ConverterFactory buildValueToEnumConverterFactory() {
        return new ValueToEnumConverterFactory();
    }

    @Deprecated
    public static ConverterFactory buildStringToEnumConverterFactory() {
        return new StringToEnumConverterFactory();
    }

    @SuppressWarnings({"unchecked", "rawTypes"})
    public static EnumRequestJSONConverter build() {
        return new EnumRequestJSONConverter();
    }

}
