package com.xyz.caofancpu.util.dataOperateUtils;

import com.xyz.caofancpu.util.dataOperateUtils.FunctionInterface.HashMapGeneratorInterface;

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
        Map<String, Object> hashMap = initHashMap(item -> {
            item.put("元素1", 2);
            item.put("元素2", "德玛西亚");
            return item;
        });
    
        // 方式一
        hashMap.keySet().stream()
                .filter(Objects::nonNull)
                .forEach(key -> System.out.println("键 = [" + key + "]\t值 = [" + hashMap.get(key) + "]"));
    
        // 方式二
        hashMap.forEach((key, value) -> System.out.println("键 = [" + key + "]\t值 = [" + value + "]"));
    }
    
}
