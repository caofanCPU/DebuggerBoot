package com.xyz.caofancpu.trackingtime.constant.enums;

import lombok.Getter;

/**
 * 成功与否状态枚举
 */
public enum SuccessStatusEnum {

    SUCCESSFUL(1, "Success"),
    FAILED(0, "Failed"),


    ;

    @Getter
    private Integer value;
    @Getter
    private String title;

    SuccessStatusEnum(Integer value, String title) {
        this.value = value;
        this.title = title;
    }
}
