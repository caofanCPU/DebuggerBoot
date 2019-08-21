package com.xyz.caofancpu.trackingtime.service.impl;

import com.xyz.caofancpu.trackingtime.mapper.TimeMarkDetailMapper;
import com.xyz.caofancpu.trackingtime.model.TimeMarkDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class TimeMarkDetailService {

    @Resource
    private TimeMarkDetailMapper timeMarkDetailMapper;

    public int insert(TimeMarkDetail pojo) {
        return timeMarkDetailMapper.insert(pojo);
    }

    public int insertList(List<TimeMarkDetail> pojos) {
        return timeMarkDetailMapper.insertList(pojos);
    }

    public List<TimeMarkDetail> select(TimeMarkDetail pojo) {
        return timeMarkDetailMapper.select(pojo);
    }

    public int update(TimeMarkDetail pojo) {
        return timeMarkDetailMapper.update(pojo);
    }

}
