package com.xyz.caofancpu.trackingtime.service.impl;

import com.github.pagehelper.PageHelper;
import com.xyz.caofancpu.trackingtime.mapper.D8gerMapper;
import com.xyz.caofancpu.trackingtime.model.D8gerMo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * D8gerMo对应的Handler
 *
 * @author 帝八哥
 */
@Service
@Slf4j
public class D8gerHandler {

    @Resource
    private D8gerMapper d8gerMapper;

    /**
     * 插入单条记录
     *
     * @param d8gerMo
     * @return
     */
    public int add(D8gerMo d8gerMo) {
        return d8gerMapper.insertSelectiveWithId(d8gerMo);
    }

    /**
     * 批量插入
     * 注意: `id` | `createTime` | `updateTime`字段将被忽略, 以数据库为准
     *
     * @param d8gerMoList
     * @return
     */
    public int batchAdd(List<D8gerMo> d8gerMoList) {
        return d8gerMapper.insertBatchWithId(d8gerMoList);
    }

    /**
     * 查询列表, 如果携带分页参数则返回分页后的列表
     *
     * @param d8gerMo
     * @param pageParams 可选分页参数
     * @return
     */
    public List<D8gerMo> queryD8gerMoList(D8gerMo d8gerMo, Integer... pageParams) {
        if (Objects.nonNull(pageParams) && pageParams.length > 0) {
            int pageNum = pageParams[0];
            int pageSize = pageParams.length > 1 ? pageParams[1] : 10;
            PageHelper.startPage(pageNum, pageSize);
        }
        return d8gerMapper.queryD8gerMoList(d8gerMo);
    }

    /**
     * 根据id更新非null字段
     *
     * @param d8gerMo
     * @return
     */
    public int updateSelectiveById(D8gerMo d8gerMo) {
        return d8gerMapper.updateByPrimaryKeySelective(d8gerMo);
    }

    /**
     * 批量根据id更新非null字段
     *
     * @param d8gerMoList
     * @return
     */
    public int batchUpdateSelectiveById(List<D8gerMo> d8gerMoList) {
        return d8gerMapper.updateBatchByPrimaryKeySelective(d8gerMoList);
    }

    /**
     * 根据id物理删除
     *
     * @param id
     * @return
     */
    public <T extends Number> int delete(T id) {
        return d8gerMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据id查询单条记录
     *
     * @param id
     * @return
     */
    public <T extends Number> D8gerMo selectByPrimaryKey(T id) {
        return d8gerMapper.selectByPrimaryKey(id);
    }

}