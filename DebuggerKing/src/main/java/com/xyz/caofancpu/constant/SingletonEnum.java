package com.xyz.caofancpu.constant;

import java.util.Arrays;

/**
 * FileName: SingletonEnum
 * Author:   caofanCPU
 * Date:     2018/9/7 16:43
 */

public enum SingletonEnum {

    REST_TEMPLATE(1, "restTemplate"),
    STANDARD_THREAD_POOL(2, "standardThreadPool"),


    ;

    private Integer singletonInstanceId;

    private String singletonInstanceName;

    SingletonEnum(Integer singletonInstanceId, String singletonInstanceName) {
        this.singletonInstanceId = singletonInstanceId;
        this.singletonInstanceName = singletonInstanceName;
    }

    public static SingletonEnum getInstanceById(Integer singletonInstanceId) {
        return Arrays.stream(SingletonEnum.values())
                .filter(item -> item.getSingletonInstanceId().equals(singletonInstanceId))
                .findFirst()
                .orElse(null);
    }

    public static SingletonEnum getInstanceById(String singletonInstanceName) {
        return Arrays.stream(SingletonEnum.values())
                .filter(item -> item.getSingletonInstanceName().equals(singletonInstanceName))
                .findFirst()
                .orElse(null);
    }

    public Integer getSingletonInstanceId() {
        return singletonInstanceId;
    }

    public String getSingletonInstanceName() {
        return singletonInstanceName;
    }

}
