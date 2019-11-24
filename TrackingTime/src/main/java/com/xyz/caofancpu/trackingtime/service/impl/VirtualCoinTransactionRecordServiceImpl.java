package com.xyz.caofancpu.trackingtime.service.impl;

import com.xyz.caofancpu.trackingtime.mapper.VirtualCoinTransactionRecordMapper;
import com.xyz.caofancpu.trackingtime.model.VirtualCoinTransactionRecord;
import com.xyz.caofancpu.trackingtime.service.VirtualCoinTransactionRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VirtualCoinTransactionRecordServiceImpl implements VirtualCoinTransactionRecordService {

    @Resource
    private VirtualCoinTransactionRecordMapper virtualCoinTransactionRecordMapper;

    public int insert(VirtualCoinTransactionRecord pojo) {
        return virtualCoinTransactionRecordMapper.insert(pojo);
    }

    public int insertList(List<VirtualCoinTransactionRecord> pojos) {
        return virtualCoinTransactionRecordMapper.insertList(pojos);
    }

    public List<VirtualCoinTransactionRecord> select(VirtualCoinTransactionRecord pojo) {
        return virtualCoinTransactionRecordMapper.select(pojo);
    }

    public int update(VirtualCoinTransactionRecord pojo) {
        return virtualCoinTransactionRecordMapper.update(pojo);
    }

}
