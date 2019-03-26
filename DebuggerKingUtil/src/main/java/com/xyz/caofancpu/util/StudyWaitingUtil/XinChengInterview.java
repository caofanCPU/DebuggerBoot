package com.xyz.caofancpu.util.StudyWaitingUtil;

import java.util.Objects;

/**
 * Copyright (C), 2000-2019, 帝八哥科技无限股份有限公司
 * FileName: XinChengInterview
 * Author:   CY_XYZ
 * Date:     2019/3/20 19:09
 * Description: ${DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class XinChengInterview {
    
    public static void main(String[] args) {
//        testThread();

//        testInstanceConstruct();
        mergeTwoOrderedArray();
    }
    
    
    public static void out(String text) {
        System.out.println(text);
    }
    
    /**
     * 关于合并两个已然有序的数组，保证结果仍然有序
     */
    public static void mergeTwoOrderedArray() {
        int[] arrA = new int[]{1, 2, 3, 4, 5};
        int[] arrB = new int[]{2, 4, 5};
        
        int[] arrResult = new int[arrA.length + arrB.length];
        int indexA = 0;
        int indexB = 0;
        int indexResult = 0;
        // 直接对俩数组元素比较，根据排序规则-递增或递减，筛选出元素，删选元素添加到新数组，同时指针++
        while (indexA < arrA.length && indexB < arrB.length) {
            if (arrA[indexA] <= arrB[indexB]) {
                arrResult[indexResult++] = arrA[indexA++];
            } else {
                arrResult[indexResult++] = arrB[indexB++];
            }
        }
        while (indexA < arrA.length) {
            arrResult[indexResult++] = arrA[indexA++];
        }
        while (indexB < arrB.length) {
            arrResult[indexResult++] = arrB[indexB++];
        }
        for (int i = 0; i < arrResult.length; i++) {
            out(arrResult[i] + ", ");
        }
    }
    
    /**
     * 保证数组是递增的
     *
     * @param originArray
     * @return
     */
    public static void sortedByAsc(int[] originArray) {
        if (Objects.isNull(originArray) || originArray.length == 1) {
            return;
        }
        for (int i = 0; i < originArray.length; i++) {
            for (int j = i + 1; j < originArray.length; j++) {
                if (originArray[j] < originArray[i]) {
                    swapElement(originArray, j, i);
                }
            }
            
        }
    }
    
    private static void swapElement(int[] originArray, int indexA, int indexB) {
        int tempValue = originArray[indexA];
        originArray[indexA] = originArray[indexB];
        originArray[indexB] = tempValue;
    }
    
    
    /**
     * 关于启动线程的测试
     */
    public static void testThread() {
        Thread sxThread = new Thread(() -> {
            out(Thread.currentThread().getName() + " 傻逼");
        });
        // 线程普通方法调用
        sxThread.run();
        
        // 调用子线程执行run方法
        sxThread.start();
        out("线程");
    }
    
    /**
     * 关于初始化子对象，静态块、构造函数的执行顺序问题
     */
    public static void testInstanceConstruct() {
        TestInstanceSunB sunB = new TestInstanceSunB();
        out("----------分界线----------");
        sunB = new TestInstanceSunB();
        sunB.conclusion();
    }
    
    /**
     * 父类
     */
    static class TestInstanceParentA {
        
        public static int initValue = 20;
        
        static {
            out("父类静态代码块执行之前的初始值为：" + initValue);
            out("父类静态代码块执行之后的初始值为：" + (initValue += 1));
        }
        
        public TestInstanceParentA() {
            out("父类构造函数执行前的初始值为：" + initValue);
            out("父类构造函数执行后的初始值为：" + (initValue += 10));
        }
        
    }
    
    /**
     * 子类
     */
    static class TestInstanceSunB extends TestInstanceParentA {
        
        static {
            out("子类静态代码块执行之前的初始值为：" + initValue);
            out("子类静态代码块执行之后的初始值为：" + (initValue += 100));
        }
        
        public TestInstanceSunB() {
            out("子类构造函数执行前的初始值为：" + initValue);
            out("子类构造函数执行后的初始值为：" + (initValue += 1000));
        }
        
        public static void conclusion() {
            out("继承机制下初始化子类的结论：\n"
                    + "1.首先是父类静态代码块->子类静态代码块->父类构造函数->子类构造函数\n"
                    + "2.此后便是父类构造函数->子类构造函数，因为静态代码块只会被执行一次，但是变量值是静态的，因而会一直保存！"
            );
            
            out("父类静态代码块执行之前的初始值为：20\n" +
                    "父类静态代码块执行之后的初始值为：21\n" +
                    "子类静态代码块执行之前的初始值为：21\n" +
                    "子类静态代码块执行之后的初始值为：121\n" +
                    "父类构造函数执行前的初始值为：121\n" +
                    "父类构造函数执行后的初始值为：131\n" +
                    "子类构造函数执行前的初始值为：131\n" +
                    "子类构造函数执行后的初始值为：1131\n" +
                    "----------分界线----------\n" +
                    "父类构造函数执行前的初始值为：1131\n" +
                    "父类构造函数执行后的初始值为：1141\n" +
                    "子类构造函数执行前的初始值为：1141\n" +
                    "子类构造函数执行后的初始值为：2141"
            );
        }
        
    }
    
}
