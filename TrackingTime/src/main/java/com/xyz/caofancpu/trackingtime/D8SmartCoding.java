package com.xyz.caofancpu.trackingtime;

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
     * score
     */
    private Long score;

    /**
     * nickName
     */
    private String nickName;

    /**
     * deleted
     */
    private Boolean deleted;

    /**
     * registerDate
     */
    private Date registerDate;

    /**
     * preStartTime
     */
    private LocalDateTime preStartTime;

    /**
     * exactMoney
     */
    private BigDecimal exactMoney;

    /**
     * englishMoney
     */
    private Double englishMoney;

    /**
     * frenchMoney
     */
    private Float frenchMoney;


    /**
     * koreanMoney
     */
    private Short koreanMoney;

    /**
     * encryptType
     */
    private EncryptTypeEnum encryptType;

    /**
     * markContentStatus
     */
    private MarkContentStatusEnum markContentStatus;

}
