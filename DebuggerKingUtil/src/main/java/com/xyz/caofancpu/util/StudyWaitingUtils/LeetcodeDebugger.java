package com.xyz.caofancpu.util.StudyWaitingUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2000-2019, 帝八哥科技无限股份有限公司
 * FileName: LeetcodeDebugger
 * Author:   CY_XYZ
 * Date:     2019/3/27 17:59
 * Description: ${DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class LeetcodeDebugger {
    
    public static void main(String[] args) {
        testAddTwoNumber();
    }
    
    public static void testAddTwoNumber() {
        int[] originArray = new int[]{1, 5, 7, 9, 2, 0};
        int targetSum = 9;
        int[] arrayResult = addTwoNumber(originArray, targetSum);
        StudyUtil.outArray(originArray, "原始数组：");
        StudyUtil.outArray(arrayResult, "要求的索引结果：");
        int[] arrayViewResult = new int[]{originArray[arrayResult[0]], originArray[arrayResult[1]], targetSum};
        StudyUtil.outArray(arrayViewResult, "索引对应的元素值及目标数字：");
    }
    
    /**
     * 给定一个数组及一个目标数字，求数组中满足两数之和的元素下表
     * 假定条件：
     * 1.数组无重复元素
     * 2.数组元素值及目标数字均为常规整数
     * 3.只有一组答案或者理解为只需要求一组答案即可
     *
     * @param originArray
     * @param targetSum
     */
    public static int[] addTwoNumber(int[] originArray, int targetSum) {
        /**
         * 分析：a + b = c；c已知, 要寻找a, b
         * 而a, b都是数组中的元素, "取反逻辑" 求和 -> 做差
         * 将问题修改为： 已知动态变化的a, 如何找到b = c - a
         * */
//        // 1.暴力法
//        for (int i = 0; i < originArray.length; i++) {
//            for (int j = 0; j < originArray.length; j++) {
//                if (originArray[i] == (targetSum - originArray[j])) {
//                    return new int[]{i, j};
//                }
//            }
//        }
        
        /*暴力法的缺点，寻找元素时需要遍历数组，浪费大量时间，而哈希表可以把寻找元素的时间缩减到常数*/
        // 2.哈希表-->HashMap，注意包含key比包含值要快一点
        Map<Integer, Integer> numberMap = new HashMap<>(originArray.length);
        for (int i = 0; i < originArray.length; i++) {
            if (numberMap.containsKey(targetSum - originArray[i])) {
                return new int[]{i, numberMap.get(targetSum - originArray[i])};
            }
            numberMap.put(originArray[i], i);
        }
        
        throw new RuntimeException("原始数组与目标数字无匹配解！");
    }
    
    
}
