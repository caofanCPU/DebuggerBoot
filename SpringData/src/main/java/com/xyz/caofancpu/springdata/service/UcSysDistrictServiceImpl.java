package com.xyz.caofancpu.springdata.service;

import com.xyz.caofancpu.springdata.mapper.UcSysDistrictMapper;
import com.xyz.caofancpu.springdata.model.UcSysDistrict;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("ucSysDistrictService")
public class UcSysDistrictServiceImpl {

    @Resource
    private UcSysDistrictMapper ucSysDistrictMapper;

    /**
     * 添加记录
     * 
     * @param ucSysDistrict
     * @return 
     */
    public int add(UcSysDistrict ucSysDistrict) {
        return ucSysDistrictMapper.add(ucSysDistrict);
    }

    /**
     * 批量插入
     * 
     * @param ucSysDistrictList
     * @return 
     */
    public int batchAdd(List<UcSysDistrict> ucSysDistrictList) {
        return ucSysDistrictMapper.batchAdd(ucSysDistrictList);
    }

    /**
     * 查询列表
     * 
     * @param ucSysDistrict
     * @return 
     */
    public List<UcSysDistrict> queryList(UcSysDistrict ucSysDistrict) {
        return ucSysDistrictMapper.queryList(ucSysDistrict);
    }
    
    /**
     * 更新记录
     * 
     * @param ucSysDistrict
     * @return 
     */
    public int update(UcSysDistrict ucSysDistrict) {
        return ucSysDistrictMapper.update(ucSysDistrict);
    }
    
    /**
     * [WARN]: 主键id物理删除
     *
     * @param id
     * @return
     */
    public int deleteById(int id) {
        return ucSysDistrictMapper.deleteById(id);
    }

}
