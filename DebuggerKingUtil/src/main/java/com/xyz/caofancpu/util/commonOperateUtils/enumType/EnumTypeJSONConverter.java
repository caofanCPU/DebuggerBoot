package com.xyz.caofancpu.util.commonOperateUtils.enumType;

import com.fasterxml.jackson.databind.util.StdConverter;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 枚举类型的JSON转换器
 *
 * @author caofanCPU
 */
public class EnumTypeJSONConverter extends StdConverter<IEnum, String> {

    @Override
    public String convert(IEnum iEnumType) {
        if (Objects.isNull(iEnumType)) {
            return StringUtils.EMPTY;
        }
        if (StringUtils.isNotBlank(iEnumType.getViewName())) {
            return iEnumType.getViewName();
        }
        return iEnumType.getName();
    }
}
