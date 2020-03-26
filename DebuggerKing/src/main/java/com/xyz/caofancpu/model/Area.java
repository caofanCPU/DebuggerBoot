package com.xyz.caofancpu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author CY_XYZ
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Area implements Serializable {

    /**
     * ID
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * pid
     */
    private Integer pid;

    /**
     * 节点深度
     */
    private Integer depth;

    /**
     * 子节点集合
     */
    private List<Area> children;

    public Area(Integer id, String name, Integer pid) {
        this.id = id;
        this.name = name;
        this.pid = pid;
    }

    public Area(Integer id, String name, Integer pid, Integer depth) {
        this.id = id;
        this.name = name;
        this.pid = pid;
        this.depth = depth;
    }
}
