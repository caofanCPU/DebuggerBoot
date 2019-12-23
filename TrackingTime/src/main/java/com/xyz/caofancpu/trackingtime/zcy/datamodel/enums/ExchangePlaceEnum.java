package com.xyz.caofancpu.trackingtime.zcy.datamodel.enums;

import com.xyz.caofancpu.util.commonoperateutils.enumtype.IEnum;
import lombok.Getter;

/**
 * 交易所类型
 */
public enum ExchangePlaceEnum implements IEnum {
    MIXIN_EXIN(1, "MIXIN EXIN"),

    ;


    @Getter
    private Integer value;

    @Getter
    private String name;

    ExchangePlaceEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
