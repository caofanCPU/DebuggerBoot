package com.xyz.caofancpu.util.commonOperateUtils;

import java.util.Objects;

/**
 * FileName: NormalUseUtil
 */

public class NormalUseUtil {

    public static void out(String text) {
        System.out.println(text);
    }

    public static void outWithoutLn(String text) {
        System.out.print(text);
    }

    /**
     * 对象转String
     *
     * @param source
     * @return
     */
    public static String convertToString(Object source) {
        return Objects.isNull(source) ? null : source.toString();
    }

    /**
     * 对象转Integer
     *
     * @param source
     * @return
     */
    public static Integer convertToInteger(Object source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return Integer.parseInt(source.toString());
    }

    /**
     * 对象转Long
     *
     * @param source
     * @return
     */
    public static Long convertToLong(Object source) {
        if (Objects.isNull(source)) {
            return null;
        }
        return Long.parseLong(source.toString());
    }
}
