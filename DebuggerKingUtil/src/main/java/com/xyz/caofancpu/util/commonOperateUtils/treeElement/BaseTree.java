package com.xyz.caofancpu.util.commonOperateUtils.treeElement;

import com.xyz.caofancpu.util.streamOperateUtils.CollectionUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * 树元素定义
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class BaseTree<C extends BaseTree> {

    /**
     * 当前节点ID
     */
    private Long id;

    /**
     * 当前节点父ID
     */
    private Long pid;

    /**
     * 当前节点深度
     */
    private int depth;

    /**
     * 当前节点的子集
     */
    private List<C> children = new ArrayList<>();

    /**
     * 判断是否是叶子节点
     *
     * @return
     */
    protected boolean hasChildren() {
        return CollectionUtil.isNotEmpty(children);
    }

}
