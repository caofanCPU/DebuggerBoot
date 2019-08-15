package com.xyz.caofancpu.trackingtime.model;

import java.util.Date;

/**
 * 时间印记签名主表
 */
public class TimeMarkContent {
    /**
     * 主键ID
     */
    private Long id;
    /**
     * 时间区块ID
     */
    private Long timeBlockId;
    /**
     * 标题
     */
    private String title;
    /**
     * 创作者ID
     */
    private Long authorId;
    /**
     * 创作者笔名
     */
    private String authorName;
    /**
     * 摘要关键字, 使用","分隔
     */
    private String keyword;
    /**
     * 签名二维码
     */
    private String signCodeUrl;
    /**
     * 状态：0-创作;1-发布;-1-删除;9-上链
     */
    private int status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

}
