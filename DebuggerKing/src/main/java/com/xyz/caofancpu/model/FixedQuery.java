package com.xyz.caofancpu.model;

/**
 * Created by caofanCPU on 2018/7/4.
 */
public class FixedQuery {
    
    private Integer id;
    
    private String name;
    
    private Boolean checked = false;
    
    public FixedQuery(Integer id, String name) {
        this.id = id;
        this.name = name;
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
    
    public Boolean getChecked() {
        return checked;
    }
    
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
    
    @Override
    public String toString() {
        return "FixedQuery{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", checked=" + checked +
                '}';
    }
}
