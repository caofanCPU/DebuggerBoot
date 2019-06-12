package com.xyz.caofancpu.util.commonOperateUtils.treeElement;

import com.xyz.caofancpu.util.streamOperateUtils.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * I为id的数据类型，推荐Integer/Long
 * E为被包装的树元素类型，限制必须实现序列化
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
class WrapTree<I extends Comparable, E extends Serializable> {
    /**
     * 树元素ID, 一般为Integer/Long型
     */
    private I id;
    /**
     * 树元素父ID, 与树元素子节点集合 必须二者择一
     */
    private I pid;
    /**
     * 节点深度, 非必须
     */
    private I depth;
    /**
     * 原始元素
     */
    private E element;
    /**
     * 树元素子节点集合
     */
    private List<WrapTree<I, E>> childElements;
    
    boolean hasChildren() {
        return CollectionUtil.isNotEmpty(childElements);
    }
}
