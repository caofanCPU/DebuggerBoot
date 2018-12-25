package com.xyz.caofancpu.springdata.mapper;

import com.xyz.caofancpu.springdata.model.UcSysDistrict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/**
 * 省市区Mapper
 *
 * @author caofanCPU
 */
@Mapper
public interface UcSysDistrictMapper {
    
    /**
     * 新增记录
     *
     * @param ucSysDistrict
     * @return
     */
    int insert(UcSysDistrict ucSysDistrict);
    
    /**
     * 批量新增记录
     *
     * @param ucSysDistrictList
     * @return
     */
    int insertList(List<UcSysDistrict> ucSysDistrictList);
    
    /**
     * 查询
     *
     * @param ucSysDistrict
     * @return
     */
    List<UcSysDistrict> select(UcSysDistrict ucSysDistrict);
    
    /**
     * 更新记录
     *
     * @param ucSysDistrict
     * @return
     */
    int update(UcSysDistrict ucSysDistrict);
    
}
