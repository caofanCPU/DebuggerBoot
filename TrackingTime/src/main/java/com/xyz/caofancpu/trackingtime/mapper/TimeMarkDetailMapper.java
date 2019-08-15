package com.xyz.caofancpu.trackingtime.mapper;

import com.xyz.caofancpu.trackingtime.model.TimeMarkDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 *
 */
@Mapper
public interface TimeMarkDetailMapper {

    int insert(@Param("pojo") TimeMarkDetail pojo);

    int insertList(@Param("pojos") List<TimeMarkDetail> pojo);

    List<TimeMarkDetail> select(@Param("pojo") TimeMarkDetail pojo);

    int update(@Param("pojo") TimeMarkDetail pojo);

}
