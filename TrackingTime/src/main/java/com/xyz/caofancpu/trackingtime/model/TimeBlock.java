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

    @ApiModelProperty(value = "主键Id", example = "98789", position = 0)
    private Long id;

    @ApiModelProperty(value = "用户ID", example = "15928", position = 1)
    private Long userId;

    @ApiModelProperty(value = "起始时间", example = "2019-08-12 12:11:10", position = 2)
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间", example = "2019-08-12 13:14:15", position = 3)
    private LocalDateTime endTime;

    @ApiModelProperty(value = "创建时间", example = "2019-08-12 13:14:15", position = 4)
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间", example = "2019-08-12 13:14:15", position = 5)
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "状态", example = "98789", position = 6)
    private SuccessStatusEnum status;

    @ApiModelProperty(value = "是否启用", example = "false", position = 7)
    private boolean enable;

}