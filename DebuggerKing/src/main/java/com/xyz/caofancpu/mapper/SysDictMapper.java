package com.xyz.caofancpu.mapper;

import com.xyz.caofancpu.model.SysConfigDictMo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by caofanCPU on 2018/7/25.
 */
@Mapper
public interface SysDictMapper {

    List<SysConfigDictMo> getInitSysDictList();

    List<SysConfigDictMo> getSysDictList();

    /**
     * 批量插入数据, 要返回的主键数据 会直接填充在 入参中
     *
     * @param dataList
     */
    void batchAddConfig(List<SysConfigDictMo> dataList);
}
