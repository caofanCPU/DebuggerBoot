package com.xyz.caofancpu.util.commonOperateUtils.enumType;

import com.xyz.caofancpu.util.streamOperateUtils.CollectionUtil;
import lombok.NonNull;

import java.util.Arrays;

/**
 * IEnum枚举工具类
 *
 * @author caofanCPU
 */
public class EnumUtil {

    /**
     * 根据枚举值查找枚举类, 找不到则返回null
     *
     * @param enumList
     * @param value
     * @param <E>
     * @return
     */
    public static <E extends Enum<E> & IEnum> E getEnumByValue(E[] enumList, @NonNull Integer value) {
        return CollectionUtil.findFirst(Arrays.asList(enumList), IEnum::getValue, value);
    }

    /**
     * 根据枚举值查找枚举类, 找不到则返回null
     *
     * @param clazz
     * @param value
     * @param <E>
     * @return
     */
    public static <E extends Enum<E> & IEnum> E getEnumByValue(Class<E> clazz, @NonNull Integer value) {
        return getEnumByValue(clazz.getEnumConstants(), value);
    }

}
