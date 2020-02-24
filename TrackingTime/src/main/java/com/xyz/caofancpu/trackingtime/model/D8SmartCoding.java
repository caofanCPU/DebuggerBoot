package com.xyz.caofancpu.trackingtime.model;

import com.xyz.caofancpu.trackingtime.constant.enums.EncryptTypeEnum;
import com.xyz.caofancpu.trackingtime.constant.enums.MarkContentStatusEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 */
public class D8SmartCoding {

    /**
     * ID
     */
    private Integer id;

    /**
     * 累计得分
     */
    private Long score;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 注册时间
     */
    private Date registerDate;

    /**
     * 试玩开始时间
     */
    private LocalDateTime preStartTime;

    /**
     * 金币
     */
    private BigDecimal exactMoney;

    /**
     * 英币
     */
    private Double englishMoney;

    /**
     * 法币
     */
    private Float frenchMoney;


    /**
     * 韩币
     */
    private Short koreanMoney;

    /**
     * 加密类型
     */
    private EncryptTypeEnum encryptType;

    /**
     * 状态
     */
    private MarkContentStatusEnum markContentStatus;

}
