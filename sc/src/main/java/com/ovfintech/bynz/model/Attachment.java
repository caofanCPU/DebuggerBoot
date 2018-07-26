package com.ovfintech.bynz.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * Created by caofanCPU on 2018/6/29.
 *
 * @author caofanCPU
 *         附件
 */
public class Attachment implements Serializable {
    
    private String name;
    
    /**
     * 附件所属的业务ID
     */
    private String busId;
    
    private String type;
    
    private Integer sortId;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;
    
    private String accessUrl;
    
    public String getAccessUrl() {
        return accessUrl;
    }
    
    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getBusId() {
        return busId;
    }
    
    public void setBusId(String busId) {
        this.busId = busId;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Integer getSortId() {
        return sortId;
    }
    
    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }
    
    public String getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    
    @Override
    public String toString() {
        return "Attachment{" +
                "name='" + name + '\'' +
                ", busId='" + busId + '\'' +
                ", type='" + type + '\'' +
                ", sortId=" + sortId +
                ", createTime=" + createTime +
                ", accessUrl='" + accessUrl + '\'' +
                '}';
    }
}
