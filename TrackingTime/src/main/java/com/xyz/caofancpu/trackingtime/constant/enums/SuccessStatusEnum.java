package com.xyz.caofancpu.trackingtime.constant.enums;

import com.xyz.caofancpu.constant.IEnum;
import lombok.Getter;

/**
 * 成功与否状态枚举
 */
public enum SuccessStatusEnum implements IEnum {

    SUCCESSFUL(1, "Success"),
    FAILED(0, "Failed"),

    ;

    @Getter
    private Integer value;

    @Getter
    private String name;

    SuccessStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
