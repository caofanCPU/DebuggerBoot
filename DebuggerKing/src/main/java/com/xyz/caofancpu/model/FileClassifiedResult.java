package com.xyz.caofancpu.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * FileName: FileClassifiedResult
 * Author:   caofanCPU
 * Date:     2018/9/5 14:57
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FileClassifiedResult implements Serializable {
    
    
    private static final long serialVersionUID = 4813493830592304471L;
    List<MiniAttachment> attachmentList;
    private String memberName;
    private String location;
    
    public String getMemberName() {
        return memberName;
    }
    
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public List<MiniAttachment> getAttachmentList() {
        return attachmentList;
    }
    
    public void setAttachmentList(List<MiniAttachment> attachmentList) {
        this.attachmentList = attachmentList;
    }
}
