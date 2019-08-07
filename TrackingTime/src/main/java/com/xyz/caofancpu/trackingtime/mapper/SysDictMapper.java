package com.xyz.caofancpu.trackingtime.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by caofanCPU on 2018/7/25.
 */
@Mapper
public interface SysDictMapper {

    List<Map<String, Object>> getInitSysDictList();

    List<Map<String, Object>> getSysDictList();

    /**
     * 批量插入数据, 要返回的主键数据 会直接填充在 入参中
     *
     * @param dataList
     */
    void batchAddConfig(List<Map<String, Object>> dataList);
}
