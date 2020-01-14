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
 * @author caofanCPU
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel
public class D8gerAutoCodingVo {

    @ApiModelProperty(value = "ID", required = false, example = "", position = 0)
    private Integer id;

    @ApiModelProperty(value = "累计得分", required = false, example = "", position = 1)
    private Long score;

    @ApiModelProperty(value = "昵称", required = false, example = "", position = 2)
    private String nickName;

    @ApiModelProperty(value = "是否删除", required = false, example = "", position = 3)
    private Boolean deleted;

    @ApiModelProperty(value = "注册时间", required = false, example = "", position = 4)
    private Date registerDate;

    @ApiModelProperty(value = "试玩开始时间", required = false, example = "", position = 5)
    private LocalDateTime preStartTime;

    @ApiModelProperty(value = "金币", required = false, example = "", position = 6)
    private BigDecimal exactMoney;

    @ApiModelProperty(value = "英币", required = false, example = "", position = 7)
    private Double englishMoney;

    @ApiModelProperty(value = "法币", required = false, example = "", position = 8)
    private Float frenchMoney;

    @ApiModelProperty(value = "韩币", required = false, example = "", position = 9)
    private Short koreanMoney;

    @ApiModelProperty(value = "加密类型", required = false, example = "", position = 10)
    private EncryptTypeEnum encryptType;

    @ApiModelProperty(value = "状态", required = false, example = "", position = 11)
    private MarkContentStatusEnum markContentStatus;

    @ApiModelProperty(value = "分页页码", required = false, example = "1", position = 12)
    private Integer pageNum;

    @ApiModelProperty(value = "分页大小", required = false, example = "10", position = 13)
    private Integer pageSize;

}