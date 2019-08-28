package com.xyz.caofancpu.util.commonOperateUtils.enumType;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Objects;

/**
 * 枚举响应转换器
 */
@Slf4j
public class EnumJSONConverter<E extends Enum<E> & IEnum> extends JsonSerializer<E> {

    @Override
    public void serialize(E e, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        if (Objects.isNull(e)) {
            return;
        }
        String name = StringUtils.isBlank(e.getViewName()) ? e.getName() : e.getViewName();
        try {
            jsonGenerator.writeString(name);
        } catch (IOException ex) {
            log.error("枚举响应转换异常, 枚举[{}]原因: {}", e, ex);
        }
    }
}
