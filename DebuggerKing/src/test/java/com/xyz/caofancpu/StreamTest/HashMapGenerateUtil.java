package com.xyz.caofancpu.StreamTest;


import com.xyz.caofancpu.StreamTest.FunctionInterface.HashMapGeneratorInterface;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * FileName: HashMapGenerateUtil
 *
 * @author: caofanCPU
 * @date: 2019/3/4 12:15
 */

public class HashMapGenerateUtil {
    
    public static <K, V> Map<K, V> initHashMap(int capacity, float loadFactor, HashMapGeneratorInterface<K, V> hashMapGenerator) {
        Map<K, V> hashMap = initEmptyHashMap(capacity, loadFactor);
        return hashMapGenerator.process(hashMap);
    }
    
    public static <K, V> Map<K, V> initHashMap(float loadFactor, HashMapGeneratorInterface<K, V> hashMapGenerator) {
        Map<K, V> hashMap = initEmptyHashMap(1 << 4, loadFactor);
        return hashMapGenerator.process(hashMap);
    }
    
    public static <K, V> Map<K, V> initHashMap(int capacity, HashMapGeneratorInterface<K, V> hashMapGenerator) {
        Map<K, V> hashMap = initEmptyHashMap(capacity, 0.75f);
        return hashMapGenerator.process(hashMap);
    }
    
    public static <K, V> Map<K, V> initHashMap(HashMapGeneratorInterface<K, V> hashMapGenerator) {
        Map<K, V> hashMap = initEmptyHashMap(1 << 4, 0.75f);
        return hashMapGenerator.process(hashMap);
    }
    
    /**
     * HashMap底层已对容量 做了 2的幂 处理
     *
     * @param capacity
     * @param loadFactor
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V> HashMap<K, V> initEmptyHashMap(int capacity, float loadFactor) {
        if (capacity <= 1) {
            capacity = 1 << 4;
        }
        if (loadFactor < 0f) {
            loadFactor = 0.75f;
        }
        int newCapacity = 1;
        while (newCapacity < capacity) {
            newCapacity <<= 1;
        }
        return new HashMap<>(newCapacity, loadFactor);
    }
    
    public static void main(String[] args) {
        Map<String, Object> hashMap = new HashMap<String, Object>(4, 0.75f) {
            {
                put("元素1", 2);
                put("元素2", "流式操作");
            }
        };
        
        /*Map<String, Object> hashMap = HashMapGenerateUtil.initHashMap(3, 0.75f, map -> {
            map.put("元素1", 2);
            map.put("元素2", "流式操作");
            return map;
        });*/
        
        // 输出Map中的元素, 键值形式
        // 方式一
        hashMap.keySet().parallelStream()
                .filter(Objects::nonNull)
                .forEach(key -> System.out.println("键 = [" + key + "]\t值 = [" + hashMap.get(key) + "]"));
        
        // 方式二
        hashMap.forEach((key, value) -> System.out.println("键 = [" + key + "]\t值 = [" + value + "]"));
    }
    
}
