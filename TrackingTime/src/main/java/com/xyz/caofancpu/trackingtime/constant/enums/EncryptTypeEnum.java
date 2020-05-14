package com.xyz.caofancpu.trackingtime.constant.enums;

import com.xyz.caofancpu.constant.IEnum;
import lombok.Getter;

import java.util.Arrays;

/**
 * 加密类型
 */
public enum EncryptTypeEnum implements IEnum {
    NAKED(0, "裸奔"),
    SYSTEM_DEFAULT(1, "统一"),
    PERSONAL(2, "私人"),

    ;

    @Getter
    private Integer value;

    @Getter
    private String name;

    EncryptTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public EncryptTypeEnum find(Integer value) {
        return Arrays.stream(EncryptTypeEnum.values()).findAny().orElse(EncryptTypeEnum.NAKED);
    }

}
