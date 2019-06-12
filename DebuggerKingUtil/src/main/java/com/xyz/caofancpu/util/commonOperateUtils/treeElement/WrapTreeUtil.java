package com.xyz.caofancpu.util.commonOperateUtils.treeElement;

import com.google.common.collect.Lists;
import com.xyz.caofancpu.util.dataOperateUtils.JSONUtil;
import com.xyz.caofancpu.util.streamOperateUtils.CollectionUtil;
import lombok.NonNull;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 树形List操作工具类
 * 要求： E必须具备id, pid字段
 */
public class WrapTreeUtil {

    /**
     * 对于给定的列表， 顺序遍历父节点->子节点，遍历到的元素添加到结果收集容器中
     *
     * @param collector     结果收集容器
     * @param sourceList    数据源
     * @param pidFunction   pid操作函数表达式
     * @param idFunction    id操作函数表达式
     * @param depthFunction 节点深度操作函数表达式
     */
    public static <I extends Comparable, C extends Serializable> void expandTreeElements(List<C> collector, List<C> sourceList, @NonNull Function<C, I> pidFunction, @NonNull Function<C, I> idFunction, Function<C, I> depthFunction) {
        List<WrapTree<I, C>> treeElements = initTreeByPid(sourceList, pidFunction, idFunction, depthFunction);
        expandTree(collector, treeElements);
    }

    /**
     * 收集末级节点
     * 对于给定的列表， 顺序遍历父节点->子节点，
     * 将[满足深度限制的非叶子节点]或[小于深度限制的叶子节点]
     * 添加到结果收集容器中
     *
     * @param collector     结果收集容器
     * @param sourceList    数据源
     * @param pidFunction   pid操作函数表达式
     * @param idFunction    id操作函数表达式
     * @param depthFunction 节点深度操作函数表达式
     * @param depth         叶子节点深度限制
     */
    public static <I extends Comparable, C extends Serializable> void collectTreeLeafElements(List<C> collector, List<C> sourceList, @NonNull Function<C, I> pidFunction, @NonNull Function<C, I> idFunction, @NonNull Function<C, I> depthFunction, @NonNull I depth) {
        List<WrapTree<I, C>> treeElements = initTreeByPid(sourceList, pidFunction, idFunction, depthFunction);
        collectTreeLeaf(collector, treeElements, depth);
    }

    /**
     * 根据pid对List做一次转换 将List<C> 转换为 List<WrapTree<I, C>>
     *
     * @param sourceList  数据源
     * @param pidFunction pid操作函数表达式
     * @param idFunction  id操作函数表达式
     * @return 树元素列表
     */
    public static <I extends Comparable, C extends Serializable> List<WrapTree<I, C>> initTreeByPid(List<C> sourceList, @NonNull Function<C, I> pidFunction, @NonNull Function<C, I> idFunction, Function<C, I> depthFunction) {
        TreeMap<I, List<C>> pidMultiMap = sourceList.stream()
                .filter(Objects::nonNull)
                .collect(TreeMap::new, (map, c) -> map.computeIfAbsent(pidFunction.apply(c), init -> new ArrayList<>()).add(c), TreeMap::putAll);
        return initTreeChildItems(pidMultiMap, idFunction, depthFunction, pidMultiMap.firstKey());
    }

    /**
     * 对于嵌套树型结构的List，将其按照自然遍历顺序平铺展开
     *
     * @param collector        收集器
     * @param sourceList       数据源
     * @param childrenFunction 子集操作函数
     */
    public static <C extends Serializable> void expandNestedListInOrder(List<C> collector, List<C> sourceList, @NonNull Function<C, List<C>> childrenFunction) {
        if (CollectionUtil.isEmpty(sourceList)) {
            return;
        }
        sourceList.stream()
                .filter(Objects::nonNull)
                .forEach(currentElement -> {
                    collector.add(currentElement);
                    if (CollectionUtil.isNotEmpty(childrenFunction.apply(currentElement))) {
                        expandNestedListInOrder(collector, childrenFunction.apply(currentElement), childrenFunction);
                    }
                });
    }

    /**
     * 收集末级节点
     * 对于给定的列表， 顺序遍历父节点->子节点，
     * 将[满足深度限制的非叶子节点]或[小于深度限制的叶子节点]
     * 添加到结果收集容器中
     *
     * @param collector        结果收集容器
     * @param sourceNestedList 数据源
     * @param childrenFunction pid操作函数表达式
     * @param idFunction       id操作函数表达式
     * @param depthFunction    节点深度操作函数表达式
     * @param depth            叶子节点深度限制
     */
    public static <I extends Comparable, C extends Serializable> void selectTreeLeafElements(List<C> collector, List<C> sourceNestedList, @NonNull Function<C, List<C>> childrenFunction, @NonNull Function<C, I> idFunction, @NonNull Function<C, I> depthFunction, @NonNull I depth) {
        List<WrapTree<I, C>> treeElements = initTreeByChildren(sourceNestedList, childrenFunction, idFunction, depthFunction);
        collectTreeLeaf(collector, treeElements, depth);
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
     * @param collector           结果收集容器
     * @param sourceNestedList    数据源
     * @param getChildrenFunction pid操作函数表达式
     * @param idFunction          id操作函数表达式
     * @param depthFunction       节点深度操作函数表达式
     * @param depth               叶子节点深度限制
     */
    @SuppressWarnings("unchecked")
    public static <I extends Comparable, C extends Serializable> void pureSelectTreeLeafElements(List<C> collector, List<C> sourceNestedList, @NonNull Function<C, List<C>> getChildrenFunction, @NonNull Function<C, C> setChildrenFunction, @NonNull Function<C, I> idFunction, @NonNull Function<C, I> depthFunction, @NonNull I depth) {
        List<WrapTree<I, C>> treeElements = initTreeByChildren(sourceNestedList, getChildrenFunction, idFunction, depthFunction);
        collectTreeLeaf(collector, treeElements, depth);
        if (CollectionUtil.isEmpty(collector)) {
            return;
        }
        List<C> pureSelectList = collector.stream()
                .filter(Objects::nonNull)
                .map(c -> {
                    // 深拷贝对象，并对其子节点置空
                    setChildrenFunction.apply(JSONUtil.deepCloneBySerialization(c));
                    return c;
                })
                .collect(Collectors.toList());
        collector.clear();
        collector.addAll(pureSelectList);
    }

    /**
     * 对嵌套树型结构的List进行装饰，根据children递归设置树元素子集
     * 将List<C> 包装为 List<WrapTree<I, C>>
     *
     * @param sourceNestedList 嵌套的List数据源
     * @param childrenFunction children操作函数表达式
     * @param idFunction       id操作函数表达式
     * @return 树元素列表
     */
    public static <I extends Comparable, C extends Serializable> List<WrapTree<I, C>> initTreeByChildren(List<C> sourceNestedList, @NonNull Function<C, List<C>> childrenFunction, @NonNull Function<C, I> idFunction, Function<C, I> depthFunction) {
        if (CollectionUtil.isEmpty(sourceNestedList)) {
            return Lists.newArrayList();
        }
        return sourceNestedList.stream()
                .filter(Objects::nonNull)
                .map(currentElement -> {
                    WrapTree<I, C> wrapTree = new WrapTree<I, C>().setId(idFunction.apply(currentElement)).setElement(currentElement);
                    if (Objects.nonNull(depthFunction)) {
                        wrapTree.setDepth(depthFunction.apply(currentElement));
                    }
                    return wrapTree.setChildElements(initTreeByChildren(childrenFunction.apply(currentElement), childrenFunction, idFunction, depthFunction));
                })
                .collect(Collectors.toList());
    }

    /**
     * 递归展开子集
     *
     * @param collector    结果搜集器
     * @param treeElements 树元素列表
     */
    private static <I extends Comparable, C extends Serializable> void expandTree(List<C> collector, List<WrapTree<I, C>> treeElements) {
        if (CollectionUtil.isEmpty(treeElements)) {
            return;
        }
        treeElements.stream()
                .filter(Objects::nonNull)
                .forEach(currentElement -> {
                    collector.add(currentElement.getElement());
                    if (currentElement.hasChildren()) {
                        expandTree(collector, currentElement.getChildElements());
                    }
                });
    }

    /**
     * 收集末级节点
     * 对于给定的列表， 顺序遍历父节点->子节点，
     * 将[满足深度限制的非叶子节点]或[小于深度限制的叶子节点]
     * 添加到结果收集容器中
     *
     * @param collector    结果收集容器
     * @param treeElements 树元素列表
     * @param depth        叶子节点深度限制
     */
    @SuppressWarnings("unchecked")
    private static <I extends Comparable, C extends Serializable> void collectTreeLeaf(List<C> collector, List<WrapTree<I, C>> treeElements, @NonNull I depth) {
        if (CollectionUtil.isEmpty(treeElements)) {
            return;
        }
        treeElements.stream()
                .filter(Objects::nonNull)
                .forEach(currentElement -> {
                    if (currentElement.hasChildren() && depth.compareTo(currentElement.getDepth()) > 0) {
                        // 遍历时子节点时，非叶子节点并且 节点深度小于 深度限制值，则进行递归查找
                        collectTreeLeaf(collector, currentElement.getChildElements(), depth);
                    } else {
                        // 否则满足收集条件， 加入结果容器中
                        collector.add(currentElement.getElement());
                    }
                });
    }

    /**
     * 根据pid递归设置树元素子集
     *
     * @param pidMultiMap   分类Map
     * @param idFunction    id操作函数
     * @param depthFunction depth操作函数
     * @param pid           pid
     * @return 树元素列表
     */
    private static <I extends Comparable, C extends Serializable> List<WrapTree<I, C>> initTreeChildItems(Map<I, List<C>> pidMultiMap, @NonNull Function<C, I> idFunction, Function<C, I> depthFunction, @NonNull I pid) {
        List<C> currentList = pidMultiMap.get(pid);
        if (CollectionUtil.isEmpty(currentList)) {
            return Lists.newArrayList();
        }
        return currentList.stream()
                .filter(Objects::nonNull)
                .map(cItem -> {
                    WrapTree<I, C> wrapTree = new WrapTree<I, C>().setPid(pid).setId(idFunction.apply(cItem));
                    if (Objects.nonNull(depthFunction)) {
                        wrapTree.setDepth(depthFunction.apply(cItem));
                    }
                    wrapTree.setElement(cItem);
                    wrapTree.setChildElements(initTreeChildItems(pidMultiMap, idFunction, depthFunction, idFunction.apply(cItem)));
                    return wrapTree;
                })
                .collect(Collectors.toList());
    }

}
