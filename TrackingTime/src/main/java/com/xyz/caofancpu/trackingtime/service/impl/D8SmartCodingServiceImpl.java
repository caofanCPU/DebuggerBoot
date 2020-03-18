package com.xyz.caofancpu.trackingtime.service.impl;

import com.github.pagehelper.PageHelper;
import com.xyz.caofancpu.trackingtime.mapper.D8SmartCodingMapper;
import com.xyz.caofancpu.trackingtime.model.D8SmartCodingMo;
import com.xyz.caofancpu.trackingtime.service.D8SmartCodingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * D8SmartCodingMo对应的ServiceImpl
 *
 * @author 帝八哥
 */
@Service
@Slf4j
public class D8SmartCodingServiceImpl implements D8SmartCodingService {

    @Resource
    private D8SmartCodingMapper d8SmartCodingMapper;

    /**
     * 插入单条记录
     *
     * @param d8SmartCodingMo
     * @return
     */
    @Override
    public int add(D8SmartCodingMo d8SmartCodingMo) {
        d8SmartCodingMo.setId(null);
        return d8SmartCodingMapper.insertWithId(d8SmartCodingMo);
    }

    /**
     * 批量插入
     *
     * @param d8SmartCodingMoList
     * @return
     */
    @Override
    public int batchAdd(List<D8SmartCodingMo> d8SmartCodingMoList) {
        d8SmartCodingMoList.forEach(item -> item.setId(null));
        return d8SmartCodingMapper.insertBatchWithId(d8SmartCodingMoList);
    }

    /**
     * 查询列表, 如果携带分页参数则返回分页后的列表
     *
     * @param d8SmartCodingMo
     * @param pageParams      可选分页参数
     * @return
     */
    @Override
    public List<D8SmartCodingMo> queryD8SmartCodingMoList(D8SmartCodingMo d8SmartCodingMo, Integer... pageParams) {
        if (Objects.nonNull(pageParams) && pageParams.length > 0) {
            int pageNum = pageParams[0];
            int pageSize = pageParams.length > 1 ? pageParams[1] : 10;
            PageHelper.startPage(pageNum, pageSize);
        }
        return d8SmartCodingMapper.queryD8SmartCodingMoList(d8SmartCodingMo);
    }

    /**
     * 根据id更新非null字段
     *
     * @param d8SmartCodingMo
     * @return
     */
    @Override
    public int updateSelectiveById(D8SmartCodingMo d8SmartCodingMo) {
        return d8SmartCodingMapper.updateByPrimaryKeySelective(d8SmartCodingMo);
    }

    /**
     * 批量根据id更新非null字段
     *
     * @param d8SmartCodingMoList
     * @return
     */
    @Override
    public int batchUpdateSelectiveById(List<D8SmartCodingMo> d8SmartCodingMoList) {
        return d8SmartCodingMapper.updateBatchByPrimaryKeySelective(d8SmartCodingMoList);
    }

    /**
     * 根据id物理删除
     *
     * @param id
     * @return
     */
    @Override
    public <T extends Number> int delete(T id) {
        return d8SmartCodingMapper.deleteByPrimaryKey(id);
    }

}