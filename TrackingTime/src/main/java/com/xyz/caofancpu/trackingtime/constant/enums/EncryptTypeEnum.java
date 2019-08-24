package com.xyz.caofancpu.trackingtime.constant.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * 加密类型
 */
public enum EncryptTypeEnum {
    NAKED(0, "裸奔"),
    SYSTEM_DEFAULT(1, "统一"),
    PERSONAL(2, "私人"),

    ;

    @Getter
    private Integer value;
    @Getter
    private String title;

    EncryptTypeEnum(Integer value, String title) {
        this.value = value;
        this.title = title;
    }

    public EncryptTypeEnum find(Integer value) {
        return Arrays.stream(EncryptTypeEnum.values()).findAny().orElse(EncryptTypeEnum.NAKED);
    }

}
