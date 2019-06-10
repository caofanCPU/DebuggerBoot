package com.xyz.caofancpu.constant;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 可以替换策略模式的枚举用法
 */
public enum FlexibleEnum {
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
    
    private int value;
    private String title;
    
    FlexibleEnum(int value, String title) {
        this.value = value;
        this.title = title;
    }
    
    public static void main(String[] args) {
        List<FlexibleEnum> flexibleEnums = Arrays.asList(FlexibleEnum.values());
        flexibleEnums.sort(Comparator.comparing(FlexibleEnum::getValue).reversed());
        flexibleEnums.forEach(FlexibleEnum::process);
    }
    
    /**
     * 定义枚举实例的抽象行为，由实例自行发挥处理
     */
    abstract void process();
    
    public int getValue() {
        return value;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void out(String text) {
        System.out.println("头衔[" + this.getTitle() + "](Rank-" + this.getValue() + "), 特征: " + text);
    }
    
}
