package com.xyz.caofancpu.trackingtime.service.impl;

import com.xyz.caofancpu.trackingtime.mapper.TimeMarkContentMapper;
import com.xyz.caofancpu.trackingtime.model.TimeMarkContent;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TimeMarkContentService {

    @Resource
    private TimeMarkContentMapper timeMarkContentMapper;

    public int insert(TimeMarkContent pojo) {
        return timeMarkContentMapper.insert(pojo);
    }

    public int insertList(List<TimeMarkContent> pojos) {
        return timeMarkContentMapper.insertList(pojos);
    }

    public List<TimeMarkContent> select(TimeMarkContent pojo) {
        return timeMarkContentMapper.select(pojo);
    }

    public int update(TimeMarkContent pojo) {
        return timeMarkContentMapper.update(pojo);
    }

}
