package com.xyz.caofancpu.constant;

import com.xyz.caofancpu.util.commonoperateutils.enumtype.IEnum;
import lombok.Getter;

/**
 * 单例对象枚举类
 *
 * @author caofanCPU
 */
public enum SingletonEnum implements IEnum {

    REST_TEMPLATE(1, "restTemplate"),
    STANDARD_THREAD_POOL(2, "standardThreadPool"),

    ;

    @Getter
    private Integer value;
    @Getter
    private String name;

    SingletonEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
