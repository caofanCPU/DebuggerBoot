package com.xyz.caofancpu.util.streamOperateUtils;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.NonNull;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * 集合工具类
 */
public class CollectionUtil extends CollectionUtils {
    
    /**
     * map判空
     *
     * @param source 数据源
     * @return 判断结果
     */
    public static boolean isEmpty(Map source) {
        return (source == null || source.isEmpty());
    }
    
    /**
     * 转换为Set, 底层默认使用HashSet
     *
     * @return Set
     */
    public static <E, R> Set<R> transToSet(Collection<E> source, Function<? super E, ? extends R> mapper) {
        return source.stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toSet());
    }
    
    /**
     * 转换为List, 底层默认使用ArrayList
     */
    public static <E, R> List<R> transToList(Collection<E> source, Function<? super E, ? extends R> mapper) {
        return source.stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
    }
    
    /**
     * 转换为指定的集合，常用Set/List，HashSet/ArrayList，LinkedSet/LinkedList
     *
     * @param resultColl
     * @param source
     * @param mapper
     */
    public static <E, R, C extends Collection<R>> C transToCollection(Supplier<C> resultColl, Collection<E> source, Function<? super E, ? extends R> mapper) {
        return source.stream().filter(Objects::nonNull).map(mapper).collect(Collectors.toCollection(resultColl));
    }
    
    /**
     * 转换为去重的List
     */
    public static <E, R> List<R> distinctList(Collection<E> source, Function<? super E, ? extends R> mapper) {
        return source.stream().filter(Objects::nonNull).map(mapper).distinct().collect(Collectors.toList());
    }
    
    /**
     * 去重
     */
    public static <E> List<E> distinctList(Collection<E> source) {
        return source.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
    }
    
    /**
     * 根据集合元素中指定的字段进行去重，返回去重后的元素集合
     *
     * @param coll
     * @param distinctComparator
     * @return
     */
    public static <T> List<T> distinctListByField(Collection<T> coll, Comparator<T> distinctComparator) {
        if (isEmpty(coll)) {
            return Collections.emptyList();
        }
        return coll.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(distinctComparator)), ArrayList::new));
    }
    
    /**
     * 转换
     */
    public static <E, A, R> R collect(Collection<E> source, Collector<? super E, A, R> collector) {
        return source.stream().filter(Objects::nonNull).collect(collector);
    }
    
    /**
     * 为避免数据丢失，Steam API底层对Collectors.toMap做了较为硬性的要求
     * 1.toMap首先不允许key重复， 因而分组时需要注意使用KEY字段
     * 2.value不允许为null
     *
     * 因而，以下*ToMap方法在使用时请注意以上两条，而*ToMapEnhance允许key重复，并启用新值替换旧值的机制
     *
     */
    
    /**
     * 分组转换为Map<K, List<V>>，底层默认HashMap<K, ArrayList<V>>
     */
    public static <E, K> Map<K, List<E>> groupIndexToMap(Collection<E> source, Function<? super E, ? extends K> kFunction) {
        return source.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(kFunction));
    }
    
    /**
     * 分组转换为指定的Map<K, List<V>>， 例如TreeMap<K, List<V>>/LinkedHashMap<K, List<V>>
     */
    public static <E, K, M extends Map<K, List<E>>> M groupIndexToMap(Supplier<M> mapColl, Collection<E> source, Function<? super E, ? extends K> kFunction) {
        return source.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(kFunction, mapColl, Collectors.toList()));
    }
    
    /**
     * 分组转换为指定Map<K, 指定的List<V>>，例如TreeMap<K, LinkedList<V>>/LinkedHashMap<K, LinkedList<V>>
     */
    public static <E, K, M extends Map<K, C>, C extends Collection<E>> M groupIndexToMap(Supplier<M> mapColl, Supplier<C> vColl, Collection<E> source, Function<? super E, ? extends K> kFunction) {
        return source.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(kFunction, mapColl, Collectors.toCollection(vColl)));
    }
    
    /**
     * 分组转换为指定Map<K, 指定的List<V>>，例如TreeMap<K, LinkedList<V>>/LinkedHashMap<K, LinkedList<V>>
     * 并且可对原始数组元素进行计算(转化)为其他对象
     */
    public static <E, K, V, M extends Map<K, C>, C extends Collection<V>> M groupIndexToMap(Supplier<M> mapColl, Supplier<C> vColl, Collection<E> source, Function<? super E, ? extends K> kGroupFunction, Function<? super E, ? extends V> vFunction) {
        return source.stream().filter(Objects::nonNull).collect(
                Collectors.groupingBy(kGroupFunction, mapColl, Collectors.mapping(vFunction, Collectors.toCollection(vColl))));
    }
    
    /**
     * 转换为Map-Value
     */
    public static <E, K> ImmutableMap<K, E> uniqueIndex(Iterable<E> values, Function<? super E, ? extends K> kFunction) {
        if (Objects.isNull(values)) {
            return ImmutableMap.of();
        }
        return Maps.uniqueIndex(values, kFunction::apply);
    }
    
    /**
     * 转换为Map-Value
     */
    public static <E, K> Map<K, E> transToMap(@NonNull Iterable<E> values, Function<? super E, ? extends K> kFunction) {
        return StreamSupport.stream(values.spliterator(), Boolean.FALSE)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(kFunction, Function.identity()));
    }
    
    /**
     * 转换为Map-Value, 重复KEY将抛出异常
     */
    public static <E, K, M extends Map<K, E>> M transToMap(Supplier<M> mapColl, @NonNull Iterable<E> values, Function<? super E, ? extends K> kFunction) {
        return StreamSupport.stream(values.spliterator(), Boolean.FALSE)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(kFunction, Function.identity(), nonDuplicateKey(), mapColl));
    }
    
    /**
     * 转换为Map-Value, 允许重复KEY
     */
    public static <E, K, M extends Map<K, E>> M transToMapEnhance(Supplier<M> mapColl, @NonNull Iterable<E> values, Function<? super E, ? extends K> kFunction) {
        return StreamSupport.stream(values.spliterator(), Boolean.FALSE)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(kFunction, Function.identity(), enableNewOnDuplicateKey(), mapColl));
    }
    
    /**
     * 转换为Map-Value
     */
    public static <E, K, V> Map<K, V> transMap(@NonNull Iterable<E> values, Function<? super E, ? extends K> kFunction, Function<? super E, ? extends V> vFunction) {
        return StreamSupport.stream(values.spliterator(), Boolean.FALSE)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(kFunction, vFunction));
    }
    
    /**
     * 转换为Map-Value, 重复KEY将抛出异常
     */
    public static <E, K, V, M extends Map<K, V>> M transToMap(Supplier<M> mapColl, @NonNull Iterable<E> values, Function<? super E, ? extends K> kFunction, Function<? super E, ? extends V> vFunction) {
        return StreamSupport.stream(values.spliterator(), Boolean.FALSE)
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(kFunction, vFunction, nonDuplicateKey(), mapColl));
    }
    
    /**
     * 按照指定分隔符将数组元素拼接为字符串
     *
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
     *
     * @param list
     * @param separator
     * @return
     */
    public static <T> String join(List<T> list, String separator) {
        if (CollectionUtil.isEmpty(list)) {
            return StringUtils.EMPTY;
        }
        List<String> stringList = transToList(list, Object::toString);
        if (Objects.isNull(separator)) {
            separator = StringUtils.EMPTY;
        }
        return stringList.stream().filter(Objects::nonNull).collect(Collectors.joining(separator));
    }
    
    /**
     * 对Map排序
     *
     * @param sourceMap
     * @param comparator
     * @param <K>
     * @param <V>
     * @return
     */
    public static <K, V extends Comparable<V>> LinkedHashMap<K, V> sortedMapByValue(Map<K, V> sourceMap, Comparator<? super Entry<K, V>> comparator) {
        if (isEmpty(sourceMap)) {
            return new LinkedHashMap<>(2, 0.5F, Boolean.FALSE);
        }
        List<Entry<K, V>> entryList = sourceMap.entrySet().stream().sorted(comparator).collect(Collectors.toList());
        return transToMap(LinkedHashMap::new, entryList, Entry::getKey, Entry::getValue);
    }
    
    /**
     * 在List中根据指定字段(函数)查找元素，找到任意一个就返回，找不到就返回null
     *
     * @param list
     * @param function
     * @param value
     * @return
     */
    public static <T, F> T findAny(List<T> list, Function<? super T, ? extends F> function, @NonNull F value) {
        return list.stream().filter(item -> value.equals(function.apply(item))).findAny().orElse(null);
    }
    
    /**
     * 在List中根据自定字段(函数)查找元素，返回找到的第一个元素，找不到就返回null
     *
     * @param list
     * @param function
     * @param value
     * @return
     */
    public static <T, F> T findFirst(List<T> list, Function<? super T, ? extends F> function, @NonNull F value) {
        return list.stream().filter(item -> value.equals(function.apply(item))).findFirst().orElse(null);
    }
    
    
    /**
     * 判断元素在list中存在至少一个值，存在就立马返回
     *
     * @param list
     * @param function
     * @param value
     * @return
     */
    public static <T, F> boolean existAtLeastOne(List<T> list, Function<? super T, ? extends F> function, @NonNull F value) {
        return list.stream().anyMatch(item -> value.equals(function.apply(item)));
    }
    
    /**
     * 判断元素在list中是否存在
     *
     * @param list
     * @param function
     * @param value
     * @return
     */
    public static <T, F> Boolean exist(List<T> list, Function<? super T, ? extends F> function, @NonNull F value) {
        return list.stream().allMatch(item -> value.equals(function.apply(item)));
    }
    
    
    /**
     * Map键值对反转
     * 示例，
     * { examA : [stu1, stu2, stu3], examB: [stu1, stu2] }
     * ⬇
     * {stu1 : [examA, examB], stu2 : [examA, examB], stu3 : [examA]}
     *
     * @param sourceMap
     * @param kFunction
     * @param vFunction
     * @return
     */
    public static <E1, E2, K1, K2> Map<K2, List<E2>> reverseKV(@NonNull Map<K1, List<E1>> sourceMap, Function<? super K1, ? extends E2> kFunction, Function<? super E1, ? extends K2> vFunction) {
        Map<K2, List<E2>> aux = new HashMap<>();
        sourceMap.entrySet().stream()
                .filter(Objects::nonNull)
                .forEach(entry -> entry.getValue().stream()
                        .filter(Objects::nonNull)
                        .forEach(v -> aux.computeIfAbsent(vFunction.apply(v), init -> new ArrayList<>()).add(kFunction.apply(entry.getKey())))
                );
        return aux;
    }
    
    /**
     * Map键值对反转，支持返回结果自定义收集容器
     * 例如返回LinkedHashMap<K, LinkedList<V>
     * <p>
     * Map<k1, Coll_1<v1>>  ==>  Map<k2, Coll_1<v2>>
     * kFunction.apply(k1) ==> v2
     * vFunction.apply(v1) ==> k2
     *
     * @param mapColl
     * @param vColl
     * @param sourceMap
     * @param kFunction
     * @param vFunction
     * @return
     */
    public static <V1, V2, K1, K2, C1 extends Collection<V1>, C2 extends Collection<V2>, M1 extends Map<K1, C1>, M2 extends Map<K2, C2>>
    M2 reverseKV(Supplier<M2> mapColl, Supplier<C2> vColl, @NonNull M1 sourceMap, Function<? super K1, ? extends V2> kFunction, Function<? super V1, ? extends K2> vFunction) {
        M2 aux = mapColl.get();
        sourceMap.entrySet().stream()
                .filter(Objects::nonNull)
                .forEach(entry -> entry.getValue().stream()
                        .filter(Objects::nonNull)
                        .forEach(v -> aux.computeIfAbsent(vFunction.apply(v), init -> vColl.get()).add(kFunction.apply(entry.getKey())))
                );
        return aux;
    }

    /**
     * 从list里根据唯一字段值 查找所有满足条件的元素
     *
     * @param list
     * @param function
     * @param value
     * @return
     */
    public static <T, F> List<T> findAll(List<T> list, Function<? super T, ? extends F> function, @NonNull F value) {
        if (isEmpty(list)) {
            return Lists.newArrayList();
        }
        return list.stream()
                .filter(Objects::nonNull)
                .filter(item -> value.equals(function.apply(item)))
                .collect(Collectors.toList());
    }

    /**
     * 针对复杂Map中，查找key匹配函数的键值对集合
     * 不满足匹配函数条件时返回空
     *
     * @param srcMap
     * @param kFunction
     * @param value
     * @return
     */
    public static <K, V, T> List<Map.Entry<K, V>> find(Map<K, V> srcMap, Function<? super K, ? extends T> kFunction, @NonNull T value) {
        if (isEmpty(srcMap)) {
            return null;
        }
        return srcMap.entrySet().stream()
                .filter(Objects::nonNull)
                .map(entry -> {
                    if (value.equals(kFunction.apply(entry.getKey()))) {
                        return entry;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    
    /**
     * 针对复杂Map中，查找key匹配函数的键值对，只取一个
     * 不满足匹配函数条件时返回null
     *
     * @param srcMap
     * @param kFunction
     * @param value
     * @return
     */
    public static <K, V, T> Map.Entry<K, V> findOne(Map<K, V> srcMap, Function<? super K, ? extends T> kFunction, @NonNull T value) {
        if (isEmpty(srcMap)) {
            return null;
        }
        return srcMap.entrySet().stream()
                .filter(Objects::nonNull)
                .filter(entry -> value.equals(kFunction.apply(entry.getKey())))
                .findAny()
                .get();
    }
    
    /**
     * 针对复杂Map中，查找key匹配函数的键值对，只取一个
     * 不满足匹配函数条件时返回null
     *
     * @param srcMap
     * @param kFunction
     * @param value
     * @return
     */
    public static <K, V, T> V findOneValue(Map<K, V> srcMap, Function<? super K, ? extends T> kFunction, @NonNull T value) {
        Entry<K, V> resultEntry = findOne(srcMap, kFunction, value);
        return Objects.isNull(resultEntry) ? null : resultEntry.getValue();
    }
    
    /**
     * Returns a merge function, suitable for use in
     * {@link Map#merge(Object, Object, BiFunction) Map.merge()} or
     * throws {@code IllegalStateException}.  This can be used to enforce the
     * assumption that the elements being collected are distinct.
     *
     * @param <T> the type of input arguments to the merge function
     * @return a merge function which always throw {@code IllegalStateException}
     */
    private static <T> BinaryOperator<T> nonDuplicateKey() {
        return (u, v) -> {
            throw new IllegalStateException(String.format("转换Map时不允许重复Key: [%s]", u));
        };
    }
    
    private static <T> BinaryOperator<T> enableNewOnDuplicateKey() {
        return (oldValue, newValue) -> newValue;
    }
    
    public static <T> Comparator<T> getNameComparator(Function<T, String> function) {
        return new NameComparator<>(function);
    }
    
    /**
     * 姓名排序（拼音首字母升序）
     **/
    public static class NameComparator<T> implements Comparator<T> {
        private Function<T, String> function;

        NameComparator(Function<T, String> function) {
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
                String pinyin1 = codePointArray1 == null ? null : codePointArray1[0];
                String pinyin2 = codePointArray2 == null ? null : codePointArray2[0];
                
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
