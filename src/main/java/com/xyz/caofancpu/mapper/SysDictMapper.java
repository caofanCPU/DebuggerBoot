package com.xyz.caofancpu.mapper;

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
    
    
}
