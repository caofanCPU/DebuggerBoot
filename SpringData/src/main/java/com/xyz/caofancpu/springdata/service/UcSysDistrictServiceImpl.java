package com.xyz.caofancpu.springdata.service;

import com.xyz.caofancpu.springdata.mapper.UcSysDistrictMapper;
import com.xyz.caofancpu.springdata.model.UcSysDistrict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author caofanCPU
 */
@Service
public class UcSysDistrictServiceImpl {
    
    @Resource
    private UcSysDistrictMapper ucSysDistrictMapper;
    
    /**
     * 添加记录
     *
     * @param ucSysDistrict
     * @return
     */
    public int insert(UcSysDistrict ucSysDistrict) {
        return ucSysDistrictMapper.insert(ucSysDistrict);
    }
    
    /**
     * 批量添加记录
     *
     * @param ucSysDistrictList
     * @return
     */
    public int insertList(List<UcSysDistrict> ucSysDistrictList) {
        return ucSysDistrictMapper.insertList(ucSysDistrictList);
    }
    
    /**
     * 查询
     *
     * @param ucSysDistrict
     * @return
     */
    public List<UcSysDistrict> select(UcSysDistrict ucSysDistrict) {
        return ucSysDistrictMapper.select(ucSysDistrict);
    }
    
    /**
     * 更新
     *
     * @param ucSysDistrict
     * @return
     */
    public int update(UcSysDistrict ucSysDistrict) {
        return ucSysDistrictMapper.update(ucSysDistrict);
    }
    
}
