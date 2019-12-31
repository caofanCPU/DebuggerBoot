package com.xyz.caofancpu.trackingtime.model;

import com.xyz.caofancpu.trackingtime.constant.enums.EncryptTypeEnum;
import com.xyz.caofancpu.trackingtime.constant.enums.MarkContentStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * D8gerAutoCodingMo
 *
 * @author 曹繁
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class D8gerAutoCodingMo {

    /**
     * ID
     */
    private Integer id;

    /**
     * 游龄
     */
    private int age;

    /**
     * 累计得分
     */
    private Long score;

    /**
     * 累计时长
     */
    private long totalSeconds;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 是否删除
     */
    private Boolean deleted;

    /**
     * 是否满级
     */
    private boolean completeLevel;

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
     * 德币
     */
    private double germanMoney;

    /**
     * 法币
     */
    private Float frenchMoney;

    /**
     * 日币
     */
    private float japanMoney;

    /**
     * 韩币
     */
    private Short koreanMoney;

    /**
     * 人民币
     */
    private short rmbMoney;

    /**
     * 加密类型
     */
    private EncryptTypeEnum encryptType;

    /**
     * 状态
     */
    private MarkContentStatusEnum markContentStatus;

}