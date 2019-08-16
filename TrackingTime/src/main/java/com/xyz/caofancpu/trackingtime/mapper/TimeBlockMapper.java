package com.xyz.caofancpu.trackingtime.mapper;

import com.xyz.caofancpu.trackingtime.model.TimeBlock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
@Mapper
public interface TimeBlockMapper {

    /**
     * 新增单条记录
     *
     * @param pojo
     * @return
     */
    int insert(TimeBlock pojo);

    int insertList(@Param("pojos") List<TimeBlock> pojos);

    List<TimeBlock> select(@Param("pojo") TimeBlock pojo);

    int update(@Param("pojo") TimeBlock pojo);

}
