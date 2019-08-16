package com.xyz.caofancpu.trackingtime.view;

import com.google.common.collect.Lists;
import com.xyz.caofancpu.trackingtime.model.TimeBlock;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 *
 */
@Data
@Accessors(chain = true)
public class TimeBlockVO {
    /**
     * 是否创建成功
     */
    private int createStatus;
    /**
     * 时间
     * 重复区块列表
     */
    private List<TimeBlock> repeatBlockList = Lists.newArrayList();
    /**
     * 成功创建的时间区块信息
     */
    private TimeBlock timeBlock;

}
