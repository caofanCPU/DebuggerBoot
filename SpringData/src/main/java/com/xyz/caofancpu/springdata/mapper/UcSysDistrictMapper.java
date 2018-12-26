package com.xyz.caofancpu.springdata.mapper;

import com.xyz.caofancpu.springdata.model.UcSysDistrict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UcSysDistrictMapper {

    /**
     * 添加记录
     * 
     * @param ucSysDistrict
     * @return 
     */
    int add(UcSysDistrict ucSysDistrict);

    /**
     * 批量插入
     * 
     * @param ucSysDistrictList
     * @return 
     */
    int batchAdd(List<UcSysDistrict> ucSysDistrictList);

    /**
     * 查询列表
     * 
     * @param ucSysDistrict
     * @return 
     */
    List<UcSysDistrict> queryList(UcSysDistrict ucSysDistrict);

    /**
     * 更新记录
     * 
     * @param ucSysDistrict
     * @return 
     */
    int update(UcSysDistrict ucSysDistrict);
    
    /**
     * [WARN]: 主键id物理删除
     *
     * @param id
     * @return
     */
    int deleteById(int id);

}
