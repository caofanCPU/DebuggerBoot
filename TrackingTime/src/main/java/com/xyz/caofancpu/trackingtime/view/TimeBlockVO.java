package com.xyz.caofancpu.trackingtime.view;

import com.google.common.collect.Lists;
import com.xyz.caofancpu.trackingtime.model.TimeBlock;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 时间区块VO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TimeBlockVO {
    @ApiModelProperty(value = "是否创建成功", name = "createStatus", example = "1")
    private int createStatus;

    @ApiModelProperty(value = "时间区块重复列表", name = "repeatBlockList", example = "[TimeBlock]")
    private List<TimeBlock> repeatBlockList = Lists.newArrayList();

    @ApiModelProperty(value = "成功创建的时间区块信息", name = "timeBlock", example = "TimeBlock")
    private TimeBlock timeBlock;

}
