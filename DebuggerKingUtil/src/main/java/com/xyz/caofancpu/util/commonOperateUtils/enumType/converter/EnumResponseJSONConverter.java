package com.xyz.caofancpu.util.commonOperateUtils.enumType.converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.xyz.caofancpu.util.commonOperateUtils.enumType.IEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * @author caofanCPU
 */
@Slf4j
public class EnumResponseJSONConverter<E extends Enum<E>> extends JsonSerializer<E> {

    @Override
    public void serialize(E enumInstance, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        if (Objects.isNull(enumInstance)) {
            return;
        }
        String name;
        if (enumInstance instanceof IEnum) {
            // 自定义
            IEnum temp = (IEnum) enumInstance;
            name = StringUtils.isBlank(temp.getViewName()) ? temp.getName() : temp.getViewName();
        } else {
            // 默认
            name = enumInstance.name();
        }

        try {
            jsonGenerator.writeString(name);
        } catch (IOException ex) {
            log.error("枚举响应转换异常, 枚举[{}]原因: {}", enumInstance.name(), ex);
        }
    }
}
