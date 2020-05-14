package com.xyz.caofancpu.trackingtime.studywaitingutils;

import java.util.Objects;

import static com.xyz.caofancpu.trackingtime.studywaitingutils.StudyUtil.out;
import static com.xyz.caofancpu.trackingtime.studywaitingutils.StudyUtil.outArray;

/**
 * Copyright (C), 2000-2019, 帝八哥科技无限股份有限公司
 * FileName: Beijing2019Interview
 * Author:   CY_XYZ
 * Date:     2019/3/20 19:09
 * Description: ${DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class Beijing2019Interview {

    public static void main(String[] args) {
//        testThread();
//        testInstanceConstruct();

        mergeTwoOrderedArray();
        unionTwoOrderedArray();
        commonTwoArray();
        differentTwoArray();

    }


    /**
     * 两个数组求并集，去重
     */
    public static void unionTwoOrderedArray() {
        int[] arrayA = new int[]{1, 2, 3, 4, 5};
        int[] arrayB = new int[]{2, 4, 5};
        outArray(arrayA, "第1个递增数组arrayA = ");
        outArray(arrayB, "第2个递增数组arrayB = ");

        int[] arrayUnion = new int[arrayA.length + arrayB.length];
        int indexA = 0;
        int indexB = 0;
        int indexUnion = 0;
        // 直接对俩数组元素比较，根据排序规则-递增或递减，筛选出元素，删选元素添加到新数组，同时指针++
        while (indexA < arrayA.length && indexB < arrayB.length) {
            if (arrayA[indexA] < arrayB[indexB]) {
                arrayUnion[indexUnion++] = arrayA[indexA++];
            } else if (arrayA[indexA] > arrayB[indexB]) {
                arrayUnion[indexUnion++] = arrayB[indexB++];
            } else {
                arrayUnion[indexUnion++] = arrayA[indexA];
                indexA++;
                indexB++;
            }
        }
        while (indexA < arrayA.length) {
            arrayUnion[indexUnion++] = arrayA[indexA++];
        }
        while (indexB < arrayB.length) {
            arrayUnion[indexUnion++] = arrayB[indexB++];
        }
        int[] arrayResult = StudyUtil.handleArrayCopyFromHead(arrayUnion, indexUnion);
        outArray(arrayResult, "两个递增数组的并集(剔除重复元素)arrayUnion = ");
        out("**********************************************\n");
//        for (int i = 0; i < arrayMerge.length; i++) {
//            out(arrayMerge[i] + ", ");
//        }
    }

    /**
     * 关于合并两个已然有序的数组，保证结果仍然有序
     */
    public static void mergeTwoOrderedArray() {
        int[] arrayA = new int[]{1, 2, 3, 4, 5};
        int[] arrayB = new int[]{2, 4, 5};
        outArray(arrayA, "第1个递增数组arrayA = ");
        outArray(arrayB, "第2个递增数组arrayB = ");

        int[] arrayMerge = new int[arrayA.length + arrayB.length];
        int indexA = 0;
        int indexB = 0;
        int indexMerge = 0;
        // 直接对俩数组元素比较，根据排序规则-递增或递减，筛选出元素，删选元素添加到新数组，同时指针++
        while (indexA < arrayA.length && indexB < arrayB.length) {
            if (arrayA[indexA] < arrayB[indexB]) {
                arrayMerge[indexMerge++] = arrayA[indexA++];
            } else {
                arrayMerge[indexMerge++] = arrayB[indexB++];
            }
        }
        while (indexA < arrayA.length) {
            arrayMerge[indexMerge++] = arrayA[indexA++];
        }
        while (indexB < arrayB.length) {
            arrayMerge[indexMerge++] = arrayB[indexB++];
        }
        int[] arrayResult = StudyUtil.handleArrayCopyFromHead(arrayMerge, indexMerge);
        outArray(arrayResult, "两个递增数组的合并(允许重复元素)arrayMerge = ");
        out("**********************************************\n");
//        for (int i = 0; i < arrayMerge.length; i++) {
//            out(arrayMerge[i] + ", ");
//        }
    }

    /**
     * 两个数组求交集，并去重
     */
    public static void commonTwoArray() {
        int[] arrayA = new int[]{1, 2, 3, 4, 5};
        int[] arrayB = new int[]{2, 4, 5};
        outArray(arrayA, "第1个递增数组arrayA = ");
        outArray(arrayB, "第2个递增数组arrayB = ");

        int commonMaxLength = Math.min(arrayA.length, arrayB.length);
        if (commonMaxLength == 0) {
            return;
        }
        int[] arrayCommon = new int[commonMaxLength];
        int indexA = 0;
        int indexB = 0;
        int indexCommon = 0;
        while (indexA < arrayA.length && indexB < arrayB.length) {
            if (arrayA[indexA] == arrayB[indexB]) {
                arrayCommon[indexCommon] = arrayA[indexA];
                indexCommon++;
                indexA++;
                indexB++;
            } else if (arrayA[indexA] > arrayB[indexB]) {
                indexB++;
            } else {
                indexA++;
            }
        }
        int[] arrayResult = StudyUtil.handleArrayCopyFromHead(arrayCommon, indexCommon);
        outArray(arrayResult, "两个递增数组的交集arrayCommon = ");
        out("**********************************************\n");
//        for (int i = 0; i < indexCommon; i++) {
//            out(arrayCommon[i] + ", ");
//        }
    }


    /**
     * 两个数组相对于第一个数组求差集
     */
    public static void differentTwoArray() {
        int[] arrayA = new int[]{1, 2, 3, 4, 5};
        int[] arrayB = new int[]{2, 4, 5};
        outArray(arrayA, "第1个递增数组arrayA = ");
        outArray(arrayB, "第2个递增数组arrayB = ");

        int[] arrayDifferentByFirst = new int[arrayA.length];
        int indexA = 0;
        int indexB = 0;
        int indexDifferent = 0;
        while (indexA < arrayA.length && indexB < arrayB.length) {
            if (arrayA[indexA] > arrayB[indexB]) {
                indexB++;
            } else if (arrayA[indexA] < arrayB[indexB]) {
                arrayDifferentByFirst[indexDifferent++] = arrayA[indexA++];
            } else {
                indexA++;
                indexB++;
            }
        }
        int[] arrayResult = StudyUtil.handleArrayCopyFromHead(arrayDifferentByFirst, indexDifferent);
        outArray(arrayResult, "两个递增数组相对于第1个数组的差集arrayDifferentByFirst = ");
        out("**********************************************\n");
//        for (int i = 0; i < indexDifferent; i++) {
//            out(arrayDifferentByFirst[i] + ", ");
//        }

    }

    /**
     * 冒泡算法：保证数组是递增的
     *
     * @param originArray
     * @return
     */
    public static void sortedByAsc(int[] originArray) {
        if (Objects.isNull(originArray) || originArray.length == 1) {
            return;
        }
        // 冒泡法排序
        for (int i = 0; i < originArray.length; i++) {
            for (int j = i + 1; j < originArray.length; j++) {
                if (originArray[j] < originArray[i]) {
                    swapElement(originArray, j, i);
                }
            }
        }
    }

    /**
     * 交换数组元素
     *
     * @param originArray
     * @param indexA
     * @param indexB
     */
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
