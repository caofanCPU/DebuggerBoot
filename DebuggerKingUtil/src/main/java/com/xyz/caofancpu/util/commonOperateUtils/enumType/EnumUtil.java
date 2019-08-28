package com.xyz.caofancpu.util.commonOperateUtils.enumType;

import com.xyz.caofancpu.util.streamOperateUtils.CollectionUtil;
import lombok.NonNull;

import java.util.Arrays;
import java.util.function.Function;

/**
 * IEnum枚举工具类
 *
 * @author caofanCPU
 */
public class EnumUtil {


    /**
     * 根据枚举指定mapper查找枚举类, 找不到则返回null
     *
     * @param enumList
     * @param mapper
     * @param value
     * @param <V>
     * @param <E>
     * @return
     */
    public static <V, E extends Enum<E> & IEnum> E getEnum(E[] enumList, @NonNull Function<E, V> mapper, @NonNull V value) {
        return CollectionUtil.findFirst(Arrays.asList(enumList), mapper, value);
    }

    /**
     * 根据枚举值查找枚举类, 找不到则返回null
     *
     * @param clazz
     * @param mapper
     * @param value
     * @param <V>
     * @param <E>
     * @return
     */
    public static <V, E extends Enum<E> & IEnum> E getEnum(Class<E> clazz, @NonNull Function<E, V> mapper, @NonNull V value) {
        return getEnum(clazz.getEnumConstants(), mapper, value);
    }


}
