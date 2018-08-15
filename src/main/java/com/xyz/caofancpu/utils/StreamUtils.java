package com.xyz.caofancpu.utils;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    
    public static void main(String[] args) {
        Map<String, Object> paramsMap = new HashMap<String, Object>(4, 0.5f){
            {
                put("name", null);
                put("age", 12L);
            }
        };
        Map<String, Object> resultMap = removeNullElement(paramsMap);
        System.out.println(resultMap);
    }
}
