package com.xyz.caofancpu.trackingtime.constant.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 可以替换策略模式的枚举用法
 */
public enum FlexibleDemoEnum {
    FIRST_LEVEL(1, "1级工程师") {
        @Override
        void process() {
            this.out("主导团队");
        }
    },

    SECOND_LEVEL(2, "2级工程师") {
        @Override
        void process() {
            this.out("专注全局设计");
        }
    },

    THIRD_LEVEL(3, "3级工程师") {
        @Override
        void process() {
            this.out("转入局部设计");
        }
    },

    FOURTH_LEVEL(4, "4级工程师") {
        @Override
        void process() {
            this.out("高效搬砖");
        }
    },

    FIFTH_LEVEL(5, "5级工程师") {
        @Override
        void process() {
            this.out("搬砖");
        }
    },

    ;

    @Getter
    private Integer value;
    @Getter
    private String title;

    FlexibleDemoEnum(Integer value, String title) {
        this.value = value;
        this.title = title;
    }

    public static void main(String[] args) {
        List<FlexibleDemoEnum> flexibleDemoEnums = Arrays.asList(FlexibleDemoEnum.values());
        flexibleDemoEnums.sort(Comparator.comparing(FlexibleDemoEnum::getValue).reversed());
        flexibleDemoEnums.forEach(FlexibleDemoEnum::process);
    }

    /**
     * 定义枚举实例的抽象行为，由实例自行发挥处理
     */
    abstract void process();

    public void out(String text) {
        System.out.println("头衔[" + this.getTitle() + "](Rank-" + this.getValue() + "), 特征: " + text);
    }

}
