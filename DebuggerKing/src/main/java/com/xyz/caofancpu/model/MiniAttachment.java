package com.xyz.caofancpu.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * FileName: MiniAttachment
 * Author:   caofanCPU
 * Date:     2018/9/5 15:01
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MiniAttachment implements Serializable {
    
    
    private static final long serialVersionUID = 7650838345674933124L;
    
    private String filePath;
    private String busId;
    
    private String namePrefix;
    
    public String getNamePrefix() {
        return namePrefix;
    }
    
    public void setNamePrefix(String namePrefix) {
        this.namePrefix = namePrefix;
    }
    
    public String getFilePath() {
        return filePath;
    }
    
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getBusId() {
        return busId;
    }
    
    public void setBusId(String busId) {
        this.busId = busId;
    }
}
