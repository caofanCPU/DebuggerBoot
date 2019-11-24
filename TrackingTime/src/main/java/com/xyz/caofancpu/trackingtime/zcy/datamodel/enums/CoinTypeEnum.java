package com.xyz.caofancpu.trackingtime.zcy.datamodel.enums;

import com.xyz.caofancpu.util.commonOperateUtils.enumType.IEnum;
import lombok.Getter;

/**
 * 币种枚举
 */
public enum CoinTypeEnum implements IEnum {
    BOX(1, "BOX"),
    BTC(2, "BTC"),
    ETH(3, "ETH"),
    PRS(4, "PRS"),
    LTC(5, "LTC"),


    ;


    @Getter
    private Integer value;

    @Getter
    private String name;

    CoinTypeEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
