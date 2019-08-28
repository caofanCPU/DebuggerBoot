package com.xyz.caofancpu.trackingtime.model;

import com.xyz.caofancpu.trackingtime.constant.enums.SuccessStatusEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 时间区块
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class TimeBlock {

    @ApiModelProperty(value = "主键Id", name = "id", example = "98789")
    private Long id;

    @ApiModelProperty(value = "用户ID", name = "id", example = "15928")
    private Long userId;

    @ApiModelProperty(value = "起始时间", name = "startTime", example = "2019-08-12 12:11:10")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间", name = "endTime", example = "2019-08-12 13:14:15")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "创建时间", name = "createTime", example = "2019-08-12 13:14:15")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", name = "updateTime", example = "2019-08-12 13:14:15")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "状态", name = "status", example = "98789")
    private SuccessStatusEnum status;

}
