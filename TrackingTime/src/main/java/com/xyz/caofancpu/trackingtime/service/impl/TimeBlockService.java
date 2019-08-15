package com.xyz.caofancpu.trackingtime.service.impl;

import com.xyz.caofancpu.trackingtime.annotion.Check;
import com.xyz.caofancpu.trackingtime.mapper.TimeBlockMapper;
import com.xyz.caofancpu.trackingtime.model.TimeBlock;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TimeBlockService {

    @Resource
    private TimeBlockMapper timeBlockMapper;

    public int insert(TimeBlock pojo) {
        return timeBlockMapper.insert(pojo);
    }

    public int insertList(List<TimeBlock> pojos) {
        return timeBlockMapper.insertList(pojos);
    }

    public List<TimeBlock> select(TimeBlock pojo) {
        return timeBlockMapper.select(pojo);
    }

    public int update(TimeBlock pojo) {
        return timeBlockMapper.update(pojo);
    }

    @Check({"userId:用户ID不能为空", "startTime:开始时间不能为空", "endTime:结束时间不能为空"})
    public void applyBlockId(TimeBlock timeBlock) {
        TimeBlock query = new TimeBlock().setUserId(timeBlock.getUserId());
        List<TimeBlock> timeBlockList = select(query);
    }

}
