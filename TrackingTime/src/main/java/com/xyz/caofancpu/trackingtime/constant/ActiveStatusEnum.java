package com.xyz.caofancpu.trackingtime.constant;

import com.xyz.caofancpu.constant.IEnum;
import lombok.Getter;

/**
 * 激活状态枚举
 */
public enum ActiveStatusEnum implements IEnum {

    ACTIVATED(1, "已激活"),
    INACTIVATED(0, "未激活"),

    ;

    @Getter
    private final Integer value;

    @Getter
    private final String name;

    ActiveStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
