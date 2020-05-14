package com.xyz.caofancpu.trackingtime.studywaitingutils;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

/**
 * FileName: SortAlgorithmUtil
 * 快速排序
 */

public class SortAlgorithmUtil {

    public static void main(String[] args) {
//        Integer[] originArray = getInitRandomArray(1000000);
        Integer[] originArray = new Integer[]{1, 4, 2, 3, 5};
//        Integer[] originArrayCopy = new Integer[originArray.length];
//        System.arraycopy(originArray, 0, originArrayCopy, 0, originArray.length);
//        Integer[] originArrayCopy2 = new Integer[originArray.length];
//        System.arraycopy(originArray, 0, originArrayCopy2, 0, originArray.length);
        Integer[] originArrayCopy3 = new Integer[originArray.length];
        System.arraycopy(originArray, 0, originArrayCopy3, 0, originArray.length);

        long startTime = System.currentTimeMillis();
        recursionFFTSort(originArray);
        long endTime = System.currentTimeMillis();
        out(Arrays.toString(originArray));
        out("递归快排消耗时间: [" + (endTime - startTime) + "ms]\n");

//        startTime = System.currentTimeMillis();
//        nonRecursionFFTSort(originArrayCopy);
//        endTime = System.currentTimeMillis();
//        out(Arrays.toString(originArrayCopy));
//        out("非递归快排消耗时间: [" + (endTime - startTime) + "ms]\n");
//
//        startTime = System.currentTimeMillis();
//        recursionMergerSort(originArrayCopy2);
//        endTime = System.currentTimeMillis();
//        out(Arrays.toString(originArrayCopy2));
//        out("递归归并排序消耗时间: [" + (endTime - startTime) + "ms]\n");
//
        startTime = System.currentTimeMillis();
        nonRecursionMergerSort(originArrayCopy3);
        endTime = System.currentTimeMillis();
        out(Arrays.toString(originArrayCopy3));
        out("非递归归并排序消耗时间: [" + (endTime - startTime) + "ms]\n");
    }

    /**
     * 测试使用数据量: [1, 5000000], 默认10000
     *
     * @param length
     * @return
     */
    public static Integer[] getInitRandomArray(int length) {
        if (length <= 0) {
            length = 10000;
        } else if (length > 5000000) {
            length = 5000000;
        }
        Random randomExecutor = new Random(System.currentTimeMillis());
        Integer[] randomArray = new Integer[length];
        for (int i = 0; i < length; i++) {
            randomArray[i] = randomExecutor.nextInt(length);
        }
        return randomArray;
    }

    public static void recursionFFTSort(Integer[] originArray) {
        doRecursionFFTSort(originArray, 0, originArray.length - 1);
    }

    public static void doRecursionFFTSort(Integer[] originArray, int start, int end) {
        if (start >= end) {
            return;
        }
        int midRoller = doFFTPartition(originArray, start, end);
        // 排序中轴左边
        doRecursionFFTSort(originArray, start, midRoller - 1);
        // 排序中轴右边
        doRecursionFFTSort(originArray, midRoller + 1, end);
    }

    public static void nonRecursionFFTSort(Integer[] originArray) {
        doNonRecursionFFTSort(originArray, 0, originArray.length - 1);
    }

    public static void doNonRecursionFFTSort(Integer[] originArray, int start, int end) {
        if (start >= end) {
            return;
        }
        // 使用栈模拟
        Stack<Integer> stack = new Stack<>();
        stack.push(end);
        stack.push(start);
        while (!stack.isEmpty()) {
            start = stack.pop();
            end = stack.pop();
            int midRoller = doFFTPartition(originArray, start, end);
            if (start < midRoller - 1) {
                stack.push(midRoller - 1);
                stack.push(start);
            }

            if (end > midRoller + 1) {
                stack.push(end);
                stack.push(midRoller + 1);
            }
        }
    }

    public static void recursionMergerSort(Integer[] originArray) {
        doRecursionMergerSort(originArray, 0, originArray.length - 1);
    }

    public static void doRecursionMergerSort(Integer[] originArray, int start, int end) {
        if (start >= end) {
            return;
        }
        int midRoller = (start + end) >> 1;
        doRecursionMergerSort(originArray, start, midRoller);
        doRecursionMergerSort(originArray, midRoller + 1, end);
        doMerge(originArray, start, midRoller, end);
    }

    public static void nonRecursionMergerSort(Integer[] originArray) {
        doNonRecursionMergerSort(originArray, 0, originArray.length - 1);
    }

    public static void doNonRecursionMergerSort(Integer[] originArray, int start, int end) {
        if (start >= end) {
            return;
        }
        // 循环一致化-while循环, 注意 + - 与 位运算的优先级
        int subLength = 1;
        while (subLength <= originArray.length) {
            int i = start;
            while (i + subLength <= end) {
                doMerge(originArray, i, i + subLength - 1, Math.min(i + (subLength << 1) - 1, originArray.length - 1));
                i += (subLength << 1);
            }
            subLength <<= 1;
        }

        // 循环一致化-for循环
        /*for (int subLength = 1; subLength <= originArray.length; subLength <<= 1) {
            for (int i = start; i + subLength <= end; i += (subLength << 1)) {
                doMerge(originArray, i, i + subLength - 1, Math.min(i + (subLength << 1) - 1, originArray.length - 1));
            }
        }*/

    }

    public static void doMerge(Integer[] originArray, int start, int midRoller, int end) {
        Integer[] tempArray = new Integer[end - start + 1];
        int i = start;
        int j = midRoller + 1;
        int k = 0;
        // 把较小的元素复制到新数组
        while (i <= midRoller && j <= end) {
            if (originArray[i] < originArray[j]) {
                tempArray[k++] = originArray[i++];
            } else {
                tempArray[k++] = originArray[j++];
            }
        }
        // 把左边剩余元素移入数组
        while (i <= midRoller) {
            tempArray[k++] = originArray[i++];
        }
        // 把右边剩余元素移入数组
        while (j <= end) {
            tempArray[k++] = originArray[j++];
        }
        System.arraycopy(tempArray, 0, originArray, start, tempArray.length);
    }


    /**
     * @param originArray
     * @param start
     * @param end
     * @return
     */
    public static int doFFTPartition(Integer[] originArray, int start, int end) {
        int referredValue = originArray[start];
        while (start < end) {
            // 一定先从end处移动
            while (originArray[end] >= referredValue && end > start) {
                end--;
            }
            if (end != start) {
                originArray[start] = originArray[end];
            }

            while (originArray[start] <= referredValue && start < end) {
                start++;
            }
            if (start != end) {
                originArray[end] = originArray[start];
            }
        }
        originArray[start] = referredValue;
        return start;
    }

    public static void out(Object out) {
        System.out.print(out + "\n");
    }

}
