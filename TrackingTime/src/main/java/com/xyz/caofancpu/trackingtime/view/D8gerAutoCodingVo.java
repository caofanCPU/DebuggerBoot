package com.xyz.caofancpu.trackingtime.view;

import com.xyz.caofancpu.trackingtime.constant.enums.EncryptTypeEnum;
import com.xyz.caofancpu.trackingtime.constant.enums.MarkContentStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * D8gerAutoCodingMo对应的SwaggerApi增强Vo对象
 *
 * @author 曹繁
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel
public class D8gerAutoCodingVo {

    @ApiModelProperty(value = "ID", required = false, example = "1", position = 0)
    private Integer id;

    @ApiModelProperty(value = "游龄", required = false, example = "100", position = 1)
    private int age;

    @ApiModelProperty(value = "累计得分", required = false, example = "233939", position = 2)
    private Long score;

    @ApiModelProperty(value = "累计时长", required = false, example = "22334", position = 3)
    private long totalSeconds;

    @ApiModelProperty(value = "昵称", required = false, example = "帝八哥", position = 4)
    private String nickName;

    @ApiModelProperty(value = "是否删除", required = false, example = "false", position = 5)
    private Boolean deleted;

    @ApiModelProperty(value = "是否满级", required = false, example = "true", position = 6)
    private boolean completeLevel;

    @ApiModelProperty(value = "注册时间", required = false, example = "", position = 7)
    private Date registerDate;

    @ApiModelProperty(value = "试玩开始时间", required = false, example = "", position = 8)
    private LocalDateTime preStartTime;

    @ApiModelProperty(value = "金币", required = false, example = "5544.33", position = 9)
    private BigDecimal exactMoney;

    @ApiModelProperty(value = "英币", required = false, example = "", position = 10)
    private Double englishMoney;

    @ApiModelProperty(value = "德币", required = false, example = "", position = 11)
    private double germanMoney;

    @ApiModelProperty(value = "法币", required = false, example = "", position = 12)
    private Float frenchMoney;

    @ApiModelProperty(value = "日币", required = false, example = "", position = 13)
    private float japanMoney;

    @ApiModelProperty(value = "韩币", required = false, example = "", position = 14)
    private Short koreanMoney;

    @ApiModelProperty(value = "人民币", required = false, example = "", position = 15)
    private short rmbMoney;

    @ApiModelProperty(value = "加密类型", required = false, example = "", position = 16)
    private EncryptTypeEnum encryptType;

    @ApiModelProperty(value = "状态", required = false, example = "", position = 17)
    private MarkContentStatusEnum markContentStatus;

    @ApiModelProperty(value = "分页页码", required = false, example = "1", position = 18)
    private Integer pageNum;

    @ApiModelProperty(value = "分页大小", required = false, example = "10", position = 19)
    private Integer pageSize;

}