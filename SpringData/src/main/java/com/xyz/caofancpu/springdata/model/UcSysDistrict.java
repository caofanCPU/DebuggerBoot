package com.xyz.caofancpu.springdata.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;


/**
 * 省市区
 * @author caofanCPU
 * @date 2018/7/2
 */
@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UcSysDistrict implements Serializable {
    /**
     * 区域ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    /**
     * 区域名称
     */
    @NonNull
    private String name;
    /**
     * 区域PID
     */
    private Integer pid;
    /**
     * 区域名称全拼首字母
     */
    private String initial;
    /**
     * 区域首字名称拼音的首字母
     */
    private String initials;
    /**
     * 区域名称全拼
     */
    private String pinyin;
    /**
     * 区域编码
     */
    private String code;
    /**
     * (弃用)区域名称全拼
     */
    private String areaCode;
    /**
     * 排序
     */
    private int sort;
    
    /**
     * 创建时间
     */
    private Date createTime;
    
    /**
     * 更新时间
     */
    private Date updateTime;
    
}
