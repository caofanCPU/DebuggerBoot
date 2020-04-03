package com.xyz.caofancpu.util.commonoperateutils.treeelement;

import com.google.common.collect.Lists;
import com.xyz.caofancpu.util.dataoperateutils.JSONUtil;
import com.xyz.caofancpu.util.streamoperateutils.CollectionUtil;
import lombok.NonNull;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 树形List操作工具类
 * 要求： E必须具备id, [pid/children]字段，主键可比较，对象可序列化
 *
 * @author caofanCPU
 */
@Deprecated
public class DeathTreeUtil {

    /**
     * 对于给定的列表， 顺序遍历父节点->子节点，遍历到的元素添加到结果收集容器中
     *
     * @param collector     结果收集容器
     * @param nonNestedList 非嵌套平铺List数据源
     * @param pidFunction   pid操作函数表达式
     * @param idFunction    id操作函数表达式
     */
    public static <I extends Comparable<I>, C extends Serializable> void expandTreeElements(List<C> collector, List<C> nonNestedList, @NonNull Function<? super C, ? extends I> pidFunction, @NonNull Function<? super C, ? extends I> idFunction) {
        List<DeathTree<I, C>> treeElements = initTreeByPid(nonNestedList, pidFunction, idFunction, null, null, null);
        expandTree(collector, treeElements, DeathTree::getElement);
    }

    /**
     * 根据深度收集末级节点
     * 对于给定的列表， 顺序遍历父节点->子节点，
     * 将[满足深度限制的非叶子节点]或[小于深度限制的叶子节点]
     * 添加到结果收集容器中
     *
     * @param collector     结果收集容器
     * @param nonNestedList 数据源
     * @param depth         叶子节点深度限制
     * @param pidFunction   pid操作函数表达式
     * @param idFunction    id操作函数表达式
     * @param depthFunction depth操作函数
     */
    public static <I extends Comparable<I>, C extends Serializable> void collectRelativeTreeLeafElements(List<C> collector, List<C> nonNestedList, @NonNull I depth, @NonNull Function<? super C, ? extends I> pidFunction, @NonNull Function<? super C, ? extends I> idFunction, @NonNull Function<? super C, ? extends I> depthFunction) {
        List<DeathTree<I, C>> treeElements = initTreeByPid(nonNestedList, pidFunction, idFunction, depthFunction, null, null);
        collectRelativeTreeLeafByDepth(collector, treeElements, depth);
    }

    /**
     * 根据pid对List做一次转换 将List<C> 转换为 List<WrapTree<I, C>>
     * 如果有节点排序值, 并且数据源中节点排序值不存在null值, 则子节点列表按照自然增序排列
     * 可用于转换结构相同但属性字段不同的'坑爹树'
     *
     * @param nonNestedList  平铺非嵌套List作为数据源, 常见于从数据库中查出的列表
     *                       如果是嵌套List, 首推{@link #initTreeByChildren}
     *                       其次, 先将嵌套List折叠展开{@link #expandNestedListInOrder}
     * @param pidFunction    pid操作函数表达式
     * @param idFunction     id操作函数表达式
     * @param depthFunction  depth操作函数
     * @param nameFunction   name操作函数
     * @param sortNoFunction sortNo操作函数
     * @param abandon        是否丢弃原始元素, 传true则丢弃, 其他情况保留
     * @return 树元素列表
     */
    public static <I extends Comparable<I>, C extends Serializable> List<DeathTree<I, C>> initTreeByPid(List<C> nonNestedList, @NonNull Function<? super C, ? extends I> pidFunction, @NonNull Function<? super C, ? extends I> idFunction, Function<? super C, ? extends I> depthFunction, Function<? super C, String> nameFunction, Function<? super C, ? extends I> sortNoFunction, boolean... abandon) {
        TreeMap<I, List<C>> pidMultiMap = nonNestedList.stream()
                .filter(Objects::nonNull)
                .collect(TreeMap::new, (map, c) -> map.computeIfAbsent(pidFunction.apply(c), init -> Lists.newArrayList()).add(c), TreeMap::putAll);
        boolean abandonOriginElement = Objects.nonNull(abandon) && abandon.length > 0 && abandon[0];
        return initTreeChildItems(pidMultiMap, pidMultiMap.firstKey(), abandonOriginElement, idFunction, depthFunction, nameFunction, sortNoFunction);
    }

    /**
     * 对于嵌套树型结构的List，将其按照自然遍历顺序平铺展开
     *
     * @param collector        收集器
     * @param nestedList       数据源
     * @param childrenFunction 子集操作函数
     */
    public static <C extends Serializable, T extends Serializable> void expandNestedListInOrder(List<T> collector, List<C> nestedList, @NonNull Function<? super C, ? extends List<C>> childrenFunction, @NonNull Function<? super C, ? extends T> mapper) {
        if (CollectionUtil.isEmpty(nestedList)) {
            return;
        }
        nestedList.stream()
                .filter(Objects::nonNull)
                .forEach(currentElement -> {
                    collector.add(mapper.apply(currentElement));
                    if (CollectionUtil.isNotEmpty(childrenFunction.apply(currentElement))) {
                        expandNestedListInOrder(collector, childrenFunction.apply(currentElement), childrenFunction, mapper);
                    }
                });
    }

    /**
     * 根据深度限制值收集末级节点
     * 对于给定的列表， 顺序遍历父节点->子节点，
     * 将[满足深度限制的非叶子节点]或[小于深度限制的叶子节点]
     * 添加到结果收集容器中
     *
     * @param collector        结果收集容器
     * @param sourceNestedList 数据源
     * @param depth            叶子节点深度限制
     * @param childrenFunction children操作函数表达式
     * @param idFunction       id操作函数表达式
     * @param depthFunction    节点深度操作函数表达式
     */
    public static <I extends Comparable<I>, C extends Serializable> void selectRelativeTreeLeafByDepth(List<C> collector, List<C> sourceNestedList, @NonNull I depth, @NonNull Function<? super C, ? extends List<C>> childrenFunction, @NonNull Function<? super C, ? extends I> idFunction, @NonNull Function<? super C, ? extends I> depthFunction) {
        List<DeathTree<I, C>> treeElements = initTreeByChildren(sourceNestedList, childrenFunction, idFunction, depthFunction, null, null);
        collectRelativeTreeLeafByDepth(collector, treeElements, depth);
    }

    /**
     * 根据节点深度裁剪树
     * 处理方式：递归遍历找到目标深度的元素，将其子集置空，剩余的元素直接返回，且返回的也是树型结构
     *
     * @param sourceNestedList 原始嵌套List
     * @param depth            指定深度
     * @param childrenFunction 子集获取函数
     * @param depthFunction    深度操作函数
     */
    public static <I extends Comparable<I>, C extends Serializable> List<C> cutTreeElementByDepth(List<C> sourceNestedList, @NonNull I depth, @NonNull Function<? super C, ? extends List<C>> childrenFunction, @NonNull Function<? super C, ? extends I> depthFunction) {
        return sourceNestedList.stream()
                .filter(Objects::nonNull)
                .map(currentElement -> {
                    // 当前元素为叶子节点，无法对子集进行操作，可以直接返回，不需要复制对象
                    if (CollectionUtil.isEmpty(childrenFunction.apply(currentElement))) {
                        return currentElement;
                    }
                    // 深拷贝对象
                    C newElement = JSONUtil.deepCloneBySerialization(currentElement);
                    // 拿到子集引用
                    List<C> children = childrenFunction.apply(newElement);
                    // 深度未达限制值且为非叶子节点，那么递归调用
                    if (depthFunction.apply(newElement).compareTo(depth) < 0) {
                        // 递归获取子集返回的list结果
                        List<C> cList = cutTreeElementByDepth(children, depth, childrenFunction, depthFunction);
                        // 子集置空
                        children.clear();
                        // 再设置子集结果
                        children.addAll(cList);
                    } else {
                        // 子集置空
                        children.clear();
                    }
                    return newElement;
                })
                .collect(Collectors.toList());
    }

    /**
     * 收集末级节点
     * 对于给定的列表， 顺序遍历父节点->子节点，
     * 将[满足深度限制的非叶子节点]或[小于深度限制的叶子节点]
     * 添加到结果收集容器中
     * <p>
     * 与selectTreeLeafElements的区别：
     * 如果深度限制值很小，那么收集容器中会有较多的非叶子节点
     * 对于嵌套型List来说，其子节点集合的数据仍在收集容器中
     * 考虑到数据使用率的问题，使用本方法将废弃数据置空
     * <p>
     * 代价/风险：借助JSONObject及元素的序列化实现深拷贝
     *
     * @param collector        结果收集容器
     * @param sourceNestedList 数据源
     * @param depth            叶子节点深度限制
     * @param childrenFunction children操作函数表达式
     * @param idFunction       id操作函数表达式
     * @param depthFunction    节点深度操作函数表达式
     */
    public static <I extends Comparable<I>, C extends Serializable> void pureSelectRelativeTreeLeafByDepth(List<C> collector, List<C> sourceNestedList, @NonNull I depth, @NonNull Function<? super C, ? extends List<C>> childrenFunction, @NonNull Function<? super C, ? extends I> idFunction, @NonNull Function<? super C, ? extends I> depthFunction) {
        List<DeathTree<I, C>> treeElements = initTreeByChildren(sourceNestedList, childrenFunction, idFunction, depthFunction, null, null);
        collectRelativeTreeLeafByDepth(collector, treeElements, depth);
        if (CollectionUtil.isEmpty(collector)) {
            return;
        }
        List<C> pureSelectList = collector.stream()
                .filter(Objects::nonNull)
                .map(c -> {
                    if (CollectionUtil.isEmpty(childrenFunction.apply(c))) {
                        return c;
                    }
                    // 深拷贝对象，根据setChildrenFunction处理其子节点
                    C newElement = JSONUtil.deepCloneBySerialization(c);
                    List<C> children = childrenFunction.apply(newElement);
                    // 子集置空
                    children.clear();
                    return newElement;
                })
                .collect(Collectors.toList());
        collector.clear();
        collector.addAll(pureSelectList);
    }

    /**
     * 对嵌套树型结构的List进行装饰，根据children递归设置树元素子集
     * 如果有节点排序值, 并且数据源中节点排序值不存在null值, 则子节点列表按照自然增序排列
     * 将List<C> 包装为 List<WrapTree<I, C>>
     *
     * @param sourceNestedList 嵌套的List数据源
     * @param childrenFunction children操作函数表达式
     * @param idFunction       id操作函数表达式
     * @param depthFunction    depth操作函数表达式
     * @param nameFunction     name操作函数表达式
     * @param sortNoFunction   sortNo操作函数表达式
     * @param abandon          是否丢弃原始元素, 传true则丢弃, 其他情况保留
     * @return 树元素列表
     */
    public static <I extends Comparable<I>, C extends Serializable> List<DeathTree<I, C>> initTreeByChildren(List<C> sourceNestedList, @NonNull Function<? super C, ? extends List<C>> childrenFunction, @NonNull Function<? super C, ? extends I> idFunction, Function<? super C, ? extends I> depthFunction, Function<? super C, String> nameFunction, Function<? super C, ? extends I> sortNoFunction, boolean... abandon) {
        if (CollectionUtil.isEmpty(sourceNestedList)) {
            return Lists.newArrayList();
        }
        boolean abandonOriginElement = Objects.nonNull(abandon) && abandon.length > 0 && abandon[0];
        List<DeathTree<I, C>> resultTreeList = sourceNestedList.stream()
                .filter(Objects::nonNull)
                .map(currentElement -> {
                    DeathTree<I, C> deathTree = new DeathTree<I, C>().setId(idFunction.apply(currentElement));
                    if (Objects.nonNull(depthFunction)) {
                        deathTree.setDepth(depthFunction.apply(currentElement));
                    }
                    if (Objects.nonNull(sortNoFunction)) {
                        deathTree.setSortNo(sortNoFunction.apply(currentElement));
                    }
                    if (Objects.nonNull(nameFunction)) {
                        deathTree.setName(nameFunction.apply(currentElement));
                    }
                    if (!abandonOriginElement) {
                        deathTree.setElement(currentElement);
                    }
                    deathTree.setChildElements(initTreeByChildren(childrenFunction.apply(currentElement), childrenFunction, idFunction, depthFunction, nameFunction, sortNoFunction, abandon));
                    // 内层排序
                    sort(deathTree.getChildElements(), sortNoFunction);
                    return deathTree;
                })
                .collect(Collectors.toList());
        // 最外层排序
        return sort(resultTreeList, sortNoFunction);
    }

    /**
     * 递归展开树, 得到平铺目标元素的平铺列表
     *
     * @param collector    结果搜集器
     * @param treeElements 树元素列表
     * @param mapper       树元素操作函数
     */
    public static <I extends Comparable<I>, C extends Serializable, T extends Serializable> void expandTree(List<T> collector, List<DeathTree<I, C>> treeElements, @NonNull Function<DeathTree<I, C>, T> mapper) {
        if (CollectionUtil.isEmpty(treeElements)) {
            return;
        }
        treeElements.stream()
                .filter(Objects::nonNull)
                .forEach(currentElement -> {
                    collector.add(mapper.apply(currentElement));
                    if (currentElement.hasChildren()) {
                        expandTree(collector, currentElement.getChildElements(), mapper);
                    }
                });
    }

    /**
     * 收集相对深度限制值得末级节点
     * 对于给定的列表， 顺序遍历父节点->子节点，
     * 将[满足深度限制的非叶子节点]或[小于深度限制的叶子节点]
     * 添加到结果收集容器中
     *
     * @param collector    结果收集容器
     * @param treeElements 树元素列表
     * @param depth        叶子节点深度限制
     */
    private static <I extends Comparable<I>, C extends Serializable> void collectRelativeTreeLeafByDepth(List<C> collector, List<DeathTree<I, C>> treeElements, @NonNull I depth) {
        if (CollectionUtil.isEmpty(treeElements)) {
            return;
        }
        treeElements.stream()
                .filter(Objects::nonNull)
                .forEach(currentElement -> {
                    if (currentElement.hasChildren() && depth.compareTo(currentElement.getDepth()) > 0) {
                        // 遍历时子节点时，非叶子节点并且 节点深度小于 深度限制值，则进行递归查找
                        collectRelativeTreeLeafByDepth(collector, currentElement.getChildElements(), depth);
                    } else {
                        // 否则满足收集条件， 加入结果容器中
                        collector.add(currentElement.getElement());
                    }
                });
    }


    /**
     * 根据pid递归设置树元素子集
     * 如果有节点排序值, 并且数据源中节点排序值不存在null值, 则子节点列表按照自然增序排列
     *
     * @param pidMultiMap          分类Map
     * @param pid                  pid
     * @param abandonOriginElement 是否丢弃原始元素
     * @param idFunction           id操作函数
     * @param depthFunction        depth操作函数
     * @param nameFunction         name操作函数
     * @param sortNoFunction       sortNo操作函数
     * @return 树元素列表
     */
    private static <I extends Comparable<I>, C extends Serializable> List<DeathTree<I, C>> initTreeChildItems(Map<I, List<C>> pidMultiMap, @NonNull I pid, boolean abandonOriginElement, @NonNull Function<? super C, ? extends I> idFunction, Function<? super C, ? extends I> depthFunction, Function<? super C, String> nameFunction, Function<? super C, ? extends I> sortNoFunction) {
        List<C> currentList = pidMultiMap.get(pid);
        if (CollectionUtil.isEmpty(currentList)) {
            return Lists.newArrayList();
        }
        List<DeathTree<I, C>> resultTreeList = currentList.stream()
                .filter(Objects::nonNull)
                .map(cItem -> {
                    DeathTree<I, C> deathTree = new DeathTree<I, C>().setPid(pid).setId(idFunction.apply(cItem));
                    if (Objects.nonNull(depthFunction)) {
                        deathTree.setDepth(depthFunction.apply(cItem));
                    }
                    if (Objects.nonNull(sortNoFunction)) {
                        deathTree.setSortNo(sortNoFunction.apply(cItem));
                    }
                    if (Objects.nonNull(nameFunction)) {
                        deathTree.setName(nameFunction.apply(cItem));
                    }
                    if (!abandonOriginElement) {
                        deathTree.setElement(cItem);
                    }
                    deathTree.setChildElements(initTreeChildItems(pidMultiMap, idFunction.apply(cItem), abandonOriginElement, idFunction, depthFunction, nameFunction, sortNoFunction));
                    // 内层排序
                    sort(deathTree.getChildElements(), sortNoFunction);
                    return deathTree;
                })
                .collect(Collectors.toList());
        // 最外层排序
        return sort(resultTreeList, sortNoFunction);
    }

    /**
     * 根据节点排序值函数排序
     *
     * @param currentChildren 当前子节点列表
     * @param sortNoFunction  节点排序值函数
     */
    private static <I extends Comparable<I>, C extends Serializable> List<DeathTree<I, C>> sort(List<DeathTree<I, C>> currentChildren, Function<? super C, ? extends I> sortNoFunction) {
        if (Objects.nonNull(sortNoFunction)) {
            // 子节点列表自然增序
            List<I> currentChildrenSortNoList = CollectionUtil.filterAndTransList(currentChildren, Objects::isNull, DeathTree::getSortNo);
            if (CollectionUtil.isEmpty(currentChildrenSortNoList) && CollectionUtil.isNotEmpty(currentChildren) && currentChildren.size() > 1) {
                currentChildren.sort(Comparator.comparing(DeathTree::getSortNo));
            }
        }
        return currentChildren;
    }

}