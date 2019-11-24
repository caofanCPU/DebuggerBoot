package com.xyz.caofancpu.trackingtime.zcy.datamodel.enums;

import com.xyz.caofancpu.util.commonOperateUtils.enumType.IEnum;
import lombok.Getter;

/**
 * 操作人ID枚举
 */
public enum OperatorIdEnum implements IEnum {
    CF(-1631895552, "帝八哥"),
    ZCY(-2147483648, "帝八嫂");


    @Getter
    private Integer value;

    @Getter
    private String name;

    OperatorIdEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

}
