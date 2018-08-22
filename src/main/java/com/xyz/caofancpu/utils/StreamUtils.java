package com.xyz.caofancpu.utils;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by caofanCPU on 2018/8/7.
 */
public class StreamUtils {
    
    /**
     * 根据指定字段过滤集合元素的数据
     *
     * @param sourceList
     * @param targetPropertySet
     * @return
     */
    public static List<Map<String, Object>> filterProperty(List<Map<String, Object>> sourceList, Set<String> targetPropertySet) {
        if (CollectionUtils.isEmpty(sourceList) || CollectionUtils.isEmpty(targetPropertySet)) {
            return null;
        }
        List<Map<String, Object>> resultList = new ArrayList<>();
        sourceList.stream().forEach(item ->
                {
                    Map<String, Object> newPropertyMap = new HashMap<>();
                    targetPropertySet.parallelStream().forEach(property ->
                    {
                        // 剔除值为null的属性，避免JSON序列化时报错：HttpMessageNotWritableException
                        if (item.get(property) != null) {
                            newPropertyMap.put(property, item.get(property));
                        }
                    });
                    resultList.add(newPropertyMap);
                }
        );
        return resultList;
    }
    
    public static Map<String, Object> filterProperty(Map<String, Object> source, Set<String> targetPropertySet) {
        if (source == null || source.isEmpty() || CollectionUtils.isEmpty(targetPropertySet)) {
            return null;
        }
        Map<String, Object> target = new HashMap<>(8, 0.75f);
        targetPropertySet.parallelStream().forEach(property -> {
            if (source.get(property) != null) {
                target.put(property, source.get(property));
            }
        });
        return target;
    }
    
    /**
     * 剔除请求中值为null的参数
     *
     * @param paramsMap
     * @return
     */
    public static Map<String, Object> removeNullElement(Map<String, Object> paramsMap) {
        if (paramsMap == null || paramsMap.isEmpty()) {
            return new HashMap<>(1, 0.5f);
        }
        /** 一般请求参数不会太多，故而使用单向顺序流即可 */
        
        // 1.首先构建流，剔除值为空的元素
        Stream<Map.Entry<String, Object>> tempStream = paramsMap.entrySet().stream()
                .filter((entry) -> entry.getValue() != null);
        // 2.从流中恢复map
        Map<String, Object> resultMap = tempStream.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return resultMap;
    }
    
    /**
     * 根据排序关键key对应的值的hashCode进行排序
     * 注意: 强烈推荐 排序key对应的数据为id类型
     *
     * @param sourceList
     * @param sortedKey
     * @param asc
     * @return
     */
    public static List<Map<String, Object>> sort(List<Map<String, Object>> sourceList, final String sortedKey, Boolean asc) {
        if (CollectionUtils.isEmpty(sourceList)) {
            return new ArrayList<>();
        }
        List<Map<String, Object>> sortedList = sourceList.stream()
                .sorted((x, y) -> x.get(sortedKey).hashCode() > y.get(sortedKey).hashCode() && asc ? -1 : 1)
                .collect(Collectors.toList());
        return sortedList;
    }
    
    public static void main(String[] args) {
        testStreamReturnSymbol();
    }
    
    public static void testStreamReturnSymbol() {
        Stream<Integer> sourceStream = Stream.of(1, 2, 3, 4, 5);
        sourceStream.forEach(item -> {
            if (item > 3) {
                return;
            }
            System.out.println("流中元素: " + item);
        });
        System.out.println("流结束后, 代码继续前行....");
    }
    
    public static void testRemoveNullElement() {
        Map<String, Object> paramsMap = new HashMap<String, Object>(4, 0.5f) {
            {
                put("name", null);
                put("age", 12L);
            }
        };
        Map<String, Object> resultMap = removeNullElement(paramsMap);
        System.out.println(resultMap);
    }
    
    public static void testIntegerHashCode() {
        Integer a = Integer.valueOf(127);
        Integer b = a.hashCode();
        if (a == b) {
            System.out.println("整型数值小于等于127,位于常量池中,因而[a==b] is OK");
        }
        a = 128;
        b = a.hashCode();
        Assert.isTrue(a == b, "不等于");
    }

    
}
