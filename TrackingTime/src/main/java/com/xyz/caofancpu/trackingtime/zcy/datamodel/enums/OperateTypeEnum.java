package com.xyz.caofancpu.trackingtime.zcy.datamodel.enums;

import com.xyz.caofancpu.constant.IEnum;
import lombok.Getter;

/**
 * 操作类型枚举
 */
public enum OperateTypeEnum implements IEnum {
    IN(1, "买入"),
    OUT(2, "卖出"),


    ;


    @Getter
    private Integer value;

    @Getter
    private String name;

    OperateTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
