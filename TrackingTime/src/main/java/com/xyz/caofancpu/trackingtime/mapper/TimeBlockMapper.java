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

    int insert(@Param("pojo") TimeBlock pojo);

    int insertList(@Param("pojos") List<TimeBlock> pojo);

    List<TimeBlock> select(@Param("pojo") TimeBlock pojo);

    int update(@Param("pojo") TimeBlock pojo);

}
