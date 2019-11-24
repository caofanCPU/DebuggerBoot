package com.xyz.caofancpu.trackingtime.mapper;

import com.xyz.caofancpu.trackingtime.model.VirtualCoinTransactionRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VirtualCoinTransactionRecordMapper {

    int insert(@Param("pojo") VirtualCoinTransactionRecord pojo);

    int insertList(@Param("pojos") List<VirtualCoinTransactionRecord> pojo);

    List<VirtualCoinTransactionRecord> select(@Param("pojo") VirtualCoinTransactionRecord pojo);

    int update(@Param("pojo") VirtualCoinTransactionRecord pojo);

}
