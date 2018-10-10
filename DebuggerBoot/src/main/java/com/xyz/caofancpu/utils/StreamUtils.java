package com.xyz.caofancpu.utils;

import jdk.nashorn.internal.ir.annotations.Ignore;
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
     * List版本:根据指定字段过滤出目标属性
     *
     * @param sourceList
     * @param targetPropertySet
     * @return
     */
    public static List<Map<String, Object>> filterProperty(
            List<Map<String, Object>> sourceList,
            Set<String> targetPropertySet) {
        if (CollectionUtils.isEmpty(sourceList) || CollectionUtils.isEmpty(targetPropertySet)) {
            return sourceList;
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
    
    /**
     * Map版本:根据指定字段过滤出目标属性
     *
     * @param source
     * @param targetPropertySet
     * @return
     */
    public static Map<String, Object> filterProperty(Map<String, Object> source, Set<String> targetPropertySet) {
        if (source == null || source.isEmpty() || CollectionUtils.isEmpty(targetPropertySet)) {
            return source;
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
     * List版本:根据指定字段剔除目标属性
     *
     * @param sourceList
     * @param targetPropertySet
     * @return
     */
    public static List<Map<String, Object>> removeProperty(
            List<Map<String, Object>> sourceList,
            Set<String> targetPropertySet) {
        if (CollectionUtils.isEmpty(sourceList) || CollectionUtils.isEmpty(targetPropertySet)) {
            return sourceList;
        }
        sourceList.stream().forEach(item -> targetPropertySet.parallelStream().forEach(property -> item.remove(property)));
        return sourceList;
    }
    
    /**
     * Map版本:根据指定字段剔除目标属性
     *
     * @param source
     * @param targetPropertySet
     * @return
     */
    public static Map<String, Object> removeProperty(Map<String, Object> source, Set<String> targetPropertySet) {
        if (source == null || source.isEmpty() || CollectionUtils.isEmpty(targetPropertySet)) {
            return source;
        }
        targetPropertySet.parallelStream().forEach(property -> {
            source.remove(property);
        });
        return source;
    }
    
    /**
     * 剔除请求中值为null的参数
     *
     * @param paramsMap
     * @return
     */
    public static Map<String, Object> removeNullElement(Map<String, Object> paramsMap) {
        if (paramsMap == null || paramsMap.isEmpty()) {
            return paramsMap;
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
        // 1.直接使用JSK8中的增强List的sort方法
        sourceList.sort((item1, item2) -> item1.get(sortedKey).hashCode() > item2.get(sortedKey).hashCode() && asc ? 1 : -1);
        return sourceList;
        
        /*// 2.使用流 + 开辟新空间 进行排序 [未推荐使用]
        List<Map<String, Object>> sortedList = sourceList.stream()
                .sorted((x, y) -> x.get(sortedKey).hashCode() > y.get(sortedKey).hashCode() && asc ? -1 : 1)
                .collect(Collectors.toList());
        return sortedList;*/
    }
    
    public static void main(String[] args) {
        testListSort();
    }
    
    @Ignore
    public static void testListSort() {
        List<Map<String, Object>> attachmentList = new ArrayList<Map<String, Object>>(10) {
            {
                add(new HashMap<String, Object>(2, 0.5f) {
                    {
                        put("id", 4);
                        put("name", "张三");
                    }
                });
                add(new HashMap<String, Object>(2, 0.5f) {
                    {
                        put("id", 3);
                        put("name", "李四");
                    }
                });
                add(new HashMap<String, Object>(2, 0.5f) {
                    {
                        put("id", 1);
                        put("name", "王五");
                    }
                });
                add(new HashMap<String, Object>(2, 0.5f) {
                    {
                        put("id", 2);
                        put("name", "赵六");
                    }
                });
                add(new HashMap<String, Object>(2, 0.5f) {
                    {
                        put("id", 2);
                        put("name", "赵六2");
                    }
                });
            }
        };
        sort(attachmentList, "id", true);
        System.out.println("排序成功!");
    }
    
    @Ignore
    public static void testFilterMapProperty() {
        Map<String, Object> sourceMap = new HashMap<String, Object>(4, 0.5f) {
            {
                put("id", 1);
                put("name", "帝八哥");
                put("age", 120L);
            }
        };
        Set<String> targetPropertySet = new HashSet<String>(4) {
            {
                add("name");
            }
        };
//        Map<String, Object> resultMap = filterProperty(sourceMap, targetPropertySet);
        Map<String, Object> resultMap = removeProperty(sourceMap, targetPropertySet);
        System.out.println(resultMap.size());
    }
    
    @Ignore
    public static void testFilterListProperty() {
        List<Map<String, Object>> sourceList = new ArrayList<Map<String, Object>>(12) {
            {
                add(new HashMap<String, Object>(4, 0.5f) {
                    {
                        put("id", 1);
                        put("name", "帝八哥");
                        put("age", 120L);
                    }
                });
                add(new HashMap<String, Object>(4, 0.5f) {
                    {
                        put("id", 2);
                        put("name", "谷歌");
                        put("age", 120L);
                    }
                });
                add(new HashMap<String, Object>(4, 0.5f) {
                    {
                        put("id", 3);
                        put("name", "百度");
                        put("age", 120L);
                    }
                });
                add(new HashMap<String, Object>(4, 0.5f) {
                    {
                        put("id", 4);
                        put("name", "阿里巴巴");
                        put("age", 120L);
                    }
                });
            }
        };
        Set<String> targetPropertySet = new HashSet<String>(4) {
            {
                add("name");
            }
        };
        List<Map<String, Object>> resultList = filterProperty(sourceList, targetPropertySet);
//        List<Map<String, Object>> resultList = filterProperty(sourceList, targetPropertySet);
        System.out.println(resultList.size());
    }
    
    @Ignore
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
    
    @Ignore
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
    
    @Ignore
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
