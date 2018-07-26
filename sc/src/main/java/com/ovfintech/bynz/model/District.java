package com.ovfintech.bynz.model;

import java.io.Serializable;

/**
 * Created by caofanCPU on 2018/7/2.
 * 省市区
 */
public class District implements Serializable, Cloneable {
    
    private Integer id;
    
    private String name;
    
    private Integer pid;
    
    private String initial;
    
    private String initials;
    
    private String pinyin;
    
    private String code;
    
    private String areaCode;
    
    private Integer sort;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getPid() {
        return pid;
    }
    
    public void setPid(Integer pid) {
        this.pid = pid;
    }
    
    public String getInitial() {
        return initial;
    }
    
    public void setInitial(String initial) {
        this.initial = initial;
    }
    
    public String getInitials() {
        return initials;
    }
    
    public void setInitials(String initials) {
        this.initials = initials;
    }
    
    public String getPinyin() {
        return pinyin;
    }
    
    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getAreaCode() {
        return areaCode;
    }
    
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
    
    public Integer getSort() {
        return sort;
    }
    
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    
    @Override
    public District clone() {
        District obj = null;
        try {
            obj = (District) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return obj;
    }
    
    @Override
    public String toString() {
        return "District{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pid=" + pid +
                ", initial='" + initial + '\'' +
                ", initials='" + initials + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", code='" + code + '\'' +
                ", areaCode='" + areaCode + '\'' +
                ", sort=" + sort +
                '}';
    }
}
