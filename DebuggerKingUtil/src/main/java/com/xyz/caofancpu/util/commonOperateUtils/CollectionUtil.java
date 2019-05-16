package com.xyz.caofancpu.util.commonOperateUtils;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * 集合工具类
 */
public class CollectionUtil extends CollectionUtils {
    
    /**
     * map判空
     * @param coll
     * @return
     */
    public static boolean isEmpty(Map coll) {
        return (coll == null || coll.isEmpty());
    }

    /**
     * 转换为Set
     * @return
     */
    public static <F, T> Set<F> transSet(Collection<T> coll, Function<? super T, ? extends F> mapper){
        if(isEmpty(coll)) {
            return Collections.emptySet();
        }
        return coll.stream().map(mapper).collect(Collectors.toSet());
    }

    /**
     * 转换为List
     */
    public static <F, T> List<F> transList(Collection<T> coll, Function<? super T, ? extends F> mapper){
        if(isEmpty(coll)) {
            return Collections.emptyList();
        }
        return coll.stream().map(mapper).collect(Collectors.toList());
    }

    /**
     * 转换为去重的List
     */
    public static <F, T> List<F> distinctList(Collection<T> coll, Function<? super T, ? extends F> mapper){
        if(isEmpty(coll)) {
            return Collections.emptyList();
        }
        return coll.stream().map(mapper).distinct().collect(Collectors.toList());
    }

    /**
     * 去重
     */
    public static <T> List<T> distinctList(Collection<T> coll){
        if(isEmpty(coll)) {
            return Collections.emptyList();
        }
        return coll.stream().distinct().collect(Collectors.toList());
    }

    /**
     * 转换
     */
    public static <T, A, R> R collect(Collection<T> coll, Collector<? super T, A, R> collector){
        return coll.stream().collect(collector);
    }

    /**
     * 转换为Map-List
     */
    public static <T, F> Map<F, List<T>> groupIndex(Collection<T> coll, Function<? super T, ? extends F> mapper){
        if(isEmpty(coll)) {
            return Collections.emptyMap();
        }
        return coll.stream().collect(Collectors.groupingBy(mapper));
    }

    /**
     * 转换为Map-List
     */
    public static <T, F, R> Map<F, List<R>> groupIndex(Collection<T> coll, Function<? super T, ? extends F> group, Function<? super T, ? extends R> mapper){
        if(isEmpty(coll)) {
            return Collections.emptyMap();
        }
        return coll.stream().collect(Collectors.groupingBy(group,Collectors.mapping(mapper, Collectors.toList())));
    }

    /**
     * 转换为Map-Value
     */
    public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterable<V> values, Function<? super V, K> function) {
        if(values==null) {
            return ImmutableMap.of();
        }
        return Maps.uniqueIndex(values, function::apply);
    }

    /**
     * 转换为Map-Value
     */
    public static <K, V> Map<K, V> transMap(Iterable<V> values, Function<? super V, K> function) {
        Map<K, V> map = new HashMap<>();
        if(values==null) {
            return map;
        }
        for (V item : values) {
            map.put(function.apply(item), item);
        }
        return map;
    }

    /**
     * 转换为Map-Value
     */
    public static <T, K, V> Map<K, V> transMap(Iterable<T> values, Function<? super T, K> keyFunction, Function<? super T, V> valueFunction) {
        Map<K, V> map = new HashMap<>();
        if(values==null) {
            return map;
        }
        for (T item : values) {
            map.put(keyFunction.apply(item), valueFunction.apply(item));
        }
        return map;
    }
    
    /**
     * 按照指定分隔符将数组元素拼接为字符串
     * @param arr
     * @param separator
     * @return
     */
    public static String join(Object[] arr, String separator) {
        if (ArrayUtils.isEmpty(arr)) {
            return StringUtils.EMPTY;
        }
        return join(Arrays.asList(arr), separator);
    }
    
    /**
     * 按照指定分隔符将数组元素拼接为字符串
     * @param list
     * @param separator
     * @return
     */
    public static String join(List<Object> list, String separator) {
        if (CollectionUtil.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        List<String> stringList = transList(list, Object::toString);
        if (Objects.isNull(separator)) {
            separator = StringUtils.EMPTY;
        }
        return stringList.stream().filter(Objects::nonNull).collect(Collectors.joining(separator));
    }

    /**
     * 保持原来顺序返回list分组
     * @param list key不会跳着分布
     * @param function
     * @return
     */
    private static <K, V> List<List<V>> groupList(List<V> list, Function<? super V, K> function) {
        List<List<V>> result = new ArrayList<>();
        K cutKey= null;
        List<V> resultItem = new ArrayList<>();
        for (V item : list) {
            K key = function.apply(item);
            if(cutKey==null || !cutKey.equals(key)) {
                cutKey = key;
                resultItem = new ArrayList<>();
                result.add(resultItem);
            }
            resultItem.add(item);
        }
        return result;
    }

    /**
     * 对map中value排序
     * @param map
     */
    public static  <K> LinkedHashMap<K, Float> sortMap(Map<K, Float> map){
        //获取entrySet
        Set<Entry<K,Float>> mapEntries = map.entrySet();

        //使用链表来对集合进行排序，使用LinkedList，利于插入元素
        List<Entry<K, Float>> result = new LinkedList<>(mapEntries);
        //自定义比较器来比较链表中的元素
        result.sort(Comparator.comparing(Entry::getValue));

        //将排好序的存入到LinkedHashMap(可保持顺序)中，需要存储键和值信息对到新的映射中。
        LinkedHashMap<K,Float> linkMap = new LinkedHashMap<>();
        for(Entry<K,Float> newEntry :result){
            linkMap.put(newEntry.getKey(), newEntry.getValue());
        }
        return linkMap;
    }


    /**
     * 从list里根据唯一字段值 查找
     * @param list
     * @param function
     * @param value
     * @return
     */
    public static <T, F> T find(List<T> list, Function<? super T, F> function, F value) {
        if(value==null) {
            return null;
        }
        if(isEmpty(list)) {
            return null;
        }
        for (T t : list) {
            if(value.equals(function.apply(t))) {
                return t;
            }
        }
        return null;
    }

    public static <T> Comparator<T> getNameComparator(Function<T, String> function) {
        return new NameComparator<T>(function);
    }

    /**
     * 姓名排序（拼音首字母升序）
     **/
    public static class NameComparator<T> implements Comparator<T> {
        private Function<T, String> function;
        public NameComparator(Function<T, String> function) {
            this.function = function;
        }

        @Override
        public int compare(T o1, T o2) {
            String name1 = function.apply(o1);
            String name2 = function.apply(o2);
            for (int i = 0; i < name1.length() && i < name2.length(); i++) {
                int codePoint1 = name1.charAt(i);
                int codePoint2 = name2.charAt(i);
                boolean supplementaryCodePoint1 = Character.isSupplementaryCodePoint(codePoint1);
                boolean supplementaryCodePoint2 = Character.isSupplementaryCodePoint(codePoint2);
                if (supplementaryCodePoint1 || supplementaryCodePoint2) {
                    i++;
                }
                if (codePoint1 != codePoint2) {
                    if (supplementaryCodePoint1 || supplementaryCodePoint2) {
                        return codePoint1 - codePoint2;
                    }
                }
                String[] codePointArray1 = PinyinHelper.toHanyuPinyinStringArray((char) codePoint1);
                String[] codePointArray2 = PinyinHelper.toHanyuPinyinStringArray((char) codePoint2);
                String pinyin1 = codePointArray1 == null ? null: codePointArray1[0];
                String pinyin2 = codePointArray2 == null ? null: codePointArray2[0];

                if (pinyin1 != null && pinyin2 != null) {
                    // 两个字符都是汉字
                    if (!pinyin1.equals(pinyin2)) {
                        return pinyin1.compareTo(pinyin2);
                    }
                } else {
                    return codePoint2 - codePoint1;
                }
            }
            return name1.length() - name2.length();
        }
    }
}
