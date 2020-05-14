package com.xyz.caofancpu.constant;

import com.xyz.caofancpu.extra.NormalUseForTestUtil;
import lombok.Getter;

/**
 * 可以替换策略模式的枚举用法
 */
public enum FlexibleEnum implements IEnum {
    FIRST_LEVEL(1, "1级工程师") {
        @Override
        void process() {
            NormalUseForTestUtil.out("主导团队");
        }
    },

    SECOND_LEVEL(2, "2级工程师") {
        @Override
        void process() {
            NormalUseForTestUtil.out("专注全局设计");
        }
    },

    THIRD_LEVEL(3, "3级工程师") {
        @Override
        void process() {
            NormalUseForTestUtil.out("转入局部设计");
        }
    },

    FOURTH_LEVEL(4, "4级工程师") {
        @Override
        void process() {
            NormalUseForTestUtil.out("高效搬砖");
        }
    },

    FIFTH_LEVEL(5, "5级工程师") {
        @Override
        void process() {
            NormalUseForTestUtil.out("搬砖");
        }
    },

    ;

    @Getter
    private Integer value;
    @Getter
    private String name;

    FlexibleEnum(int value, String title) {
        this.value = value;
        this.name = title;
    }

    /**
     * 定义枚举实例的抽象行为，由实例自行发挥处理
     */
    abstract void process();

}
