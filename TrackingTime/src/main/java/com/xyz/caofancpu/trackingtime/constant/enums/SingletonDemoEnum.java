package com.xyz.caofancpu.trackingtime.constant.enums;

import lombok.Getter;

import java.util.Arrays;

/**
 * FileName: SingletonDemoEnum
 * Author:   caofanCPU
 * Date:     2018/9/7 16:43
 */

public enum SingletonDemoEnum {

    REST_TEMPLATE(1, "restTemplate"),
    STANDARD_THREAD_POOL(2, "standardThreadPool"),


    ;

    @Getter
    private Integer singletonInstanceId;
    @Getter
    private String singletonInstanceName;

    SingletonDemoEnum(Integer singletonInstanceId, String singletonInstanceName) {
        this.singletonInstanceId = singletonInstanceId;
        this.singletonInstanceName = singletonInstanceName;
    }

    public static SingletonDemoEnum getInstanceById(Integer singletonInstanceId) {
        return Arrays.stream(SingletonDemoEnum.values())
                .filter(item -> item.getSingletonInstanceId().equals(singletonInstanceId))
                .findFirst()
                .orElse(null);
    }

    public static SingletonDemoEnum getInstanceById(String singletonInstanceName) {
        return Arrays.stream(SingletonDemoEnum.values())
                .filter(item -> item.getSingletonInstanceName().equals(singletonInstanceName))
                .findFirst()
                .orElse(null);
    }

}
