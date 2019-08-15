package com.xyz.caofancpu.trackingtime.model;

import java.util.Date;

/**
 * 时间印记详表
 */
public class TimeMarkDetail {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 主表关联ID
     */
    private Long markContentId;
    /**
     * 内容
     */
    private String content;
    /**
     * 内容签名
     */
    private String contentSignature;
    /**
     * 加密类型: 0-裸奔;1-统一;2-私人
     */
    private Integer encryptType;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
