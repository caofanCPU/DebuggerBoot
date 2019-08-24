package com.xyz.caofancpu.trackingtime.service.impl;

import com.xyz.caofancpu.trackingtime.annotation.Check;
import com.xyz.caofancpu.trackingtime.constant.enums.SuccessStatusEnum;
import com.xyz.caofancpu.trackingtime.mapper.TimeBlockMapper;
import com.xyz.caofancpu.trackingtime.model.TimeBlock;
import com.xyz.caofancpu.trackingtime.view.TimeBlockVO;
import com.xyz.caofancpu.util.dataOperateUtils.DateUtil;
import com.xyz.caofancpu.util.result.GlobalErrorInfoException;
import com.xyz.caofancpu.util.streamOperateUtils.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.MutablePair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TimeBlockService {

    @Resource
    private TimeBlockMapper timeBlockMapper;

    public int insertList(List<TimeBlock> timeBlockList) {
        return timeBlockMapper.insertList(timeBlockList);
    }

    public List<TimeBlock> queryList(TimeBlock timeBlock) {
        return timeBlockMapper.select(timeBlock);
    }

    public int update(TimeBlock timeBlock) {
        return timeBlockMapper.update(timeBlock);
    }

    @Check({"userId not empty:用户ID不能为空", "startTime not empty:开始时间不能为空", "endTime not empty:结束时间不能为空"})
    public TimeBlockVO applyBlockId(TimeBlock timeBlock)
            throws GlobalErrorInfoException {
        TimeBlock query = new TimeBlock().setUserId(timeBlock.getUserId())
                .setStartTime(DateUtil.getTodayQueryStartDate())
                .setEndTime(DateUtil.getTodayQueryEndDate());
        List<TimeBlock> timeBlockList = queryList(query);
        if (CollectionUtil.isEmpty(timeBlockList)) {
            timeBlockMapper.insert(timeBlock);
            return new TimeBlockVO().setCreateStatus(SuccessStatusEnum.SUCCESSFUL.getValue()).setTimeBlock(timeBlock);
        }
        MutablePair<Date, Date> referPairDateTimePair = new MutablePair<>(timeBlock.getStartTime(), timeBlock.getEndTime());
        List<TimeBlock> repeatBlockList = timeBlockList.stream()
                .filter(item -> {
                    // 过滤出重复区块
                    MutablePair<Date, Date> targetPairDateTimePair = new MutablePair<>(item.getStartTime(), item.getEndTime());
                    return DateUtil.hasRepeatByJavaUtilDate(targetPairDateTimePair, referPairDateTimePair);
                })
                .collect(Collectors.toList());
        if (CollectionUtil.isEmpty(repeatBlockList)) {
            timeBlockMapper.insert(timeBlock);
            return new TimeBlockVO().setCreateStatus(SuccessStatusEnum.SUCCESSFUL.getValue()).setTimeBlock(timeBlock);
        }
        return new TimeBlockVO().setCreateStatus(SuccessStatusEnum.FAILED.getValue()).setRepeatBlockList(repeatBlockList);
    }

}
