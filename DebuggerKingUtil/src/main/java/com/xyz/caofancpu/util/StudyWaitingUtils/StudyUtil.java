package com.xyz.caofancpu.util.StudyWaitingUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * Copyright (C), 2000-2019, 帝八哥科技无限股份有限公司
 * FileName: StudyUtil
 * Author:   CY_XYZ
 * Date:     2019/3/27 18:06
 * Description: ${DESCRIPTION}
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */

public class StudyUtil {

    /**
     * 从起点复制一定长度的数组元素
     *
     * @param originArray
     * @param copyLength
     * @return
     */
    public static int[] handleArrayCopyFromHead(int[] originArray, int copyLength) {
        if (Objects.isNull(originArray) || originArray.length <= copyLength) {
            return originArray;
        }
        int[] arrayResult = new int[copyLength];
        System.arraycopy(originArray, 0, arrayResult, 0, copyLength);
        return arrayResult;
    }

    public static void out(String text) {
        System.out.println(text);
    }

    /**
     * 数组输出元素
     *
     * @param originArray
     * @param text
     */
    public static void outArray(int[] originArray, String text) {
        out(text + Arrays.toString(originArray));
    }

}
