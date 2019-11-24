package com.xyz.caofancpu.trackingtime.service;


import com.xyz.caofancpu.trackingtime.model.VirtualCoinTransactionRecord;

import java.util.List;

/**
 *
 */
public interface VirtualCoinTransactionRecordService {

    int insert(VirtualCoinTransactionRecord record);

    List<VirtualCoinTransactionRecord> select(VirtualCoinTransactionRecord record);
}

