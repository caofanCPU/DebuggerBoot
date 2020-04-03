package com.xyz.caofancpu.util.commonoperateutils.treeelement;

import com.xyz.caofancpu.util.streamoperateutils.CollectionUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * I为id的数据类型，推荐Integer/Long
 * E为被包装的树元素类型，限制必须实现序列化
 *
 * @author caofanCPU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel
@Deprecated
public class WrapTree<I extends Comparable<I>, E extends Serializable> implements Serializable {
    /**
     * 树元素ID, 一般为Integer/Long型
     */
    @ApiModelProperty(value = "元素节点ID", example = "400", position = 1)
    private I id;

    /**
     * 树元素父ID, 与树元素子节点集合 必须二者择一
     * 当数据源是平铺List(从数据库查出的列表), 推荐使用pid转换
     */
    @ApiModelProperty(value = "元素父节点ID", example = "0", position = 2)
    private I pid;

    /**
     * 节点名称, 非必须
     */
    @ApiModelProperty(value = "元素节点名称", example = "节点名称", position = 3)
    private String name;

    /**
     * 节点深度, 非必须
     */
    @ApiModelProperty(value = "节点深度(第几层节点)", example = "3", position = 4)
    private I depth;

    /**
     * 节点在子列表里的相对排序值, 非必须
     */
    @ApiModelProperty(value = "节点相对排序值", example = "55", position = 5)
    private I sortNo;

    /**
     * 原始元素
     */
    @ApiModelProperty(value = "原始元素, 仅后台使用", hidden = true, position = 5)
    private E element;

    /**
     * 树元素子节点集合
     * 当树型元素是嵌套List, 推荐使用children转换
     */
    @ApiModelProperty(value = "子节点列表", position = 6)
    private List<WrapTree<I, E>> childElements;

    public boolean hasChildren() {
        return CollectionUtil.isNotEmpty(childElements);
    }
}
