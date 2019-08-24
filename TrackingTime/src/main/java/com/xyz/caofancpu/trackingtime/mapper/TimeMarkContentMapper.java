package com.xyz.caofancpu.trackingtime.mapper;

import com.xyz.caofancpu.trackingtime.model.TimeMarkContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 */
@Mapper
public interface TimeMarkContentMapper {

    int insert(TimeMarkContent pojo);

    int insertList(@Param("pojos") List<TimeMarkContent> pojos);

    List<TimeMarkContent> select(@Param("pojo") TimeMarkContent pojo);

    int update(@Param("pojo") TimeMarkContent pojo);

}
