package com.xyz.caofancpu.util.commonOperateUtils.treeElement;

import com.xyz.caofancpu.util.streamOperateUtils.CollectionUtil;
import lombok.NonNull;

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
    public static <I extends Comparable, C> void expandTreeElements(List<C> collector, List<C> sourceList, @NonNull Function<C, I> pidFunction, @NonNull Function<C, I> idFunction, Function<C, I> depthFunction) {
        List<WrapTree<I, C>> treeElements = initTree(sourceList, pidFunction, idFunction, depthFunction);
        expandTree(collector, treeElements);
    }
    
    /**
     * 收集末级节点
     * 对于给定的列表， 顺序遍历父节点->子节点，只将叶子节点添加到结果收集容器中
     *
     * @param collector     结果收集容器
     * @param sourceList    数据源
     * @param pidFunction   pid操作函数表达式
     * @param idFunction    id操作函数表达式
     * @param depthFunction 节点深度操作函数表达式
     * @param depth         叶子节点深度限制
     */
    public static <I extends Comparable, C> void collectTreeLeafElements(List<C> collector, List<C> sourceList, @NonNull Function<C, I> pidFunction, @NonNull Function<C, I> idFunction, @NonNull Function<C, I> depthFunction, @NonNull I depth) {
        List<WrapTree<I, C>> treeElements = initTree(sourceList, pidFunction, idFunction, depthFunction);
        collectTreeLeaf(collector, treeElements, depth);
        
    }
    
    /**
     * 对List做一次转换 将List<C> 转换为 List<WrapTree<I, C>>
     *
     * @param sourceList  数据源
     * @param pidFunction pid操作函数表达式
     * @param idFunction  id操作函数表达式
     * @return 树元素列表
     */
    private static <I extends Comparable, C> List<WrapTree<I, C>> initTree(List<C> sourceList, @NonNull Function<C, I> pidFunction, @NonNull Function<C, I> idFunction, Function<C, I> depthFunction) {
        TreeMap<I, List<C>> pidMultiMap = sourceList.stream()
                .filter(Objects::nonNull)
                .collect(TreeMap::new, (map, c) -> map.computeIfAbsent(pidFunction.apply(c), init -> new ArrayList<>()).add(c), TreeMap::putAll);
        return initTreeChildItems(pidMultiMap, idFunction, depthFunction, pidMultiMap.firstKey());
    }
    
    /**
     * 递归展开子集
     *
     * @param collector    结果搜集器
     * @param treeElements 树元素列表
     */
    private static <I extends Comparable, C> void expandTree(List<C> collector, List<WrapTree<I, C>> treeElements) {
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
     * 递归收集末级节点
     * 对于给定的列表， 顺序遍历父节点->子节点，只将叶子节点添加到结果收集容器中
     *
     * @param collector    结果收集容器
     * @param treeElements 树元素列表
     * @param depth        叶子节点深度限制
     */
    @SuppressWarnings("unchecked")
    private static <I extends Comparable, C> void collectTreeLeaf(List<C> collector, List<WrapTree<I, C>> treeElements, @NonNull I depth) {
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
     * 递归设置树元素子集
     *
     * @param pidMultiMap 分类Map
     * @param idFunction  id操作函数
     * @param pid         pid
     * @return 树元素列表
     */
    private static <I extends Comparable, C> List<WrapTree<I, C>> initTreeChildItems(Map<I, List<C>> pidMultiMap, @NonNull Function<C, I> idFunction, Function<C, I> depthFunction, @NonNull I pid) {
        List<C> currentList = pidMultiMap.get(pid);
        if (CollectionUtil.isEmpty(currentList)) {
            return new ArrayList<>();
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
