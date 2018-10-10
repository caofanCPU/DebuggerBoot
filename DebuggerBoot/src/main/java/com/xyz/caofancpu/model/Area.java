package com.xyz.caofancpu.model;

import java.util.List;

/**
 * @author CY_XYZ
 * @date 2018/7/29
 */
public class Area {
    
    private Integer id;
    
    private String name;
    
    private Integer pid;
    
    private List<Area> children;
    
    /**
     * 提供默认构造方法, 供反射使用
     */
    public Area() {
    }
    
    /**
     * 带参构造方法, 必须提供无参构造方法
     */
    public Area(Integer id, String name, Integer pid) {
        this.id = id;
        this.name = name;
        this.pid = pid;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getPid() {
        return pid;
    }
    
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    
    public List<Area> getChildren() {
        return children;
    }
    
    public void setChildren(List<Area> children) {
        this.children = children;
    }
    
    @Override
    public String toString() {
        return "Area{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pid=" + pid +
                ", children=" + children +
                '}';
    }
}
