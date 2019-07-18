package com.xyz.caofancpu.util.commonOperateUtils.treeElement;

import com.xyz.caofancpu.util.streamOperateUtils.CollectionUtil;

import java.util.List;
import java.util.Objects;

/**
 * 树元素工具类
 * 要求：想实现树操作的元素需继承BaseTree
 */
public class BaseTreeUtil {

    /**
     * 对于给定的列表， 顺序遍历父节点->子节点，遍历到的元素添加到结果收集容器中
     *
     * @param collector       结果收集容器
     * @param currentElements
     */
    public static <E extends BaseTree> void expandTreeElements(List<E> collector, List<E> currentElements) {
        if (CollectionUtil.isEmpty(currentElements)) {
            return;
        }
        currentElements.stream()
                .filter(Objects::nonNull)
                .forEach(currentElement -> {
                    collector.add(currentElement);
                    if (currentElement.hasChildren()) {
                        expandTreeElements(collector, currentElement.getChildren());
                    }
                });
    }

    /**
     * 收集末级节点
     * 对于给定的列表， 顺序遍历父节点->子节点，只将叶子节点添加到结果收集容器中
     *
     * @param currentElements
     * @param depth           叶子节点深度限制
     * @param collector       结果收集容器
     */
    public static <E extends BaseTree> void findTreeLeafElements(List<E> collector, List<E> currentElements, Integer depth) {
        if (CollectionUtil.isEmpty(currentElements)) {
            return;
        }
        currentElements.stream()
                .filter(Objects::nonNull)
                .forEach(currentElement -> {
                    if (currentElement.hasChildren() && currentElement.getDepth() < depth) {
                        // 遍历时子节点时，非叶子节点并且 节点深度小于 深度限制值，则进行递归查找
                        findTreeLeafElements(collector, currentElement.getChildren(), depth);
                    } else {
                        // 否则满足收集条件， 加入结果容器中
                        collector.add(currentElement);
                    }
                });
    }

}
