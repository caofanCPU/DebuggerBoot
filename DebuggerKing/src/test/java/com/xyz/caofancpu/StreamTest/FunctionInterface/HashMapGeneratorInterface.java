package com.xyz.caofancpu.StreamTest.FunctionInterface;

import java.util.Map;

/**
 * FileName: HashMapGeneratorInterface
 *
 * @author: caofanCPU
 * @date: 2019/3/4 12:12
 */
@FunctionalInterface
public interface HashMapGeneratorInterface<K, V> {
    
    /**
     * 该函数名称, 入参, 返回值不影响任意
     *
     * @param hashMap
     * @return
     */
    Map<K, V> process(Map<K, V> hashMap);
    
    /**
     * JDK1.8新特性, 接口允许声明多个 [默认方法]
     */
    default void doNothing() {}
    
}
