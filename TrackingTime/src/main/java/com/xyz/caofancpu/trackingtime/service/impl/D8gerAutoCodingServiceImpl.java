package com.xyz.caofancpu.trackingtime.service.impl;

import com.github.pagehelper.PageHelper;
import com.xyz.caofancpu.trackingtime.mapper.D8gerAutoCodingMapper;
import com.xyz.caofancpu.trackingtime.model.D8gerAutoCodingMo;
import com.xyz.caofancpu.trackingtime.service.D8gerAutoCodingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * D8gerAutoCodingMo对应的ServiceImpl
 *
 * @author 曹繁
 */
@Service
@Slf4j
public class D8gerAutoCodingServiceImpl implements D8gerAutoCodingService {

    @Resource
    private D8gerAutoCodingMapper d8gerAutoCodingMapper;

    /**
     * 插入单条记录
     *
     * @param d8gerAutoCodingMo
     * @return
     */
    @Override
    public int add(D8gerAutoCodingMo d8gerAutoCodingMo) {
        d8gerAutoCodingMo.setId(null);
        return d8gerAutoCodingMapper.insertWithId(d8gerAutoCodingMo);
    }

    /**
     * 批量插入
     *
     * @param d8gerAutoCodingMoList
     * @return
     */
    @Override
    public int batchAdd(List<D8gerAutoCodingMo> d8gerAutoCodingMoList) {
        d8gerAutoCodingMoList.forEach(item -> item.setId(null));
        return d8gerAutoCodingMapper.insertBatchWithId(d8gerAutoCodingMoList);
    }

    /**
     * 查询列表, 如果携带分页参数则返回分页后的列表
     *
     * @param d8gerAutoCodingMo
     * @param pageParams        可选分页参数
     * @return
     */
    @Override
    public List<D8gerAutoCodingMo> queryD8gerAutoCodingMoList(D8gerAutoCodingMo d8gerAutoCodingMo, Integer... pageParams) {
        if (Objects.nonNull(pageParams) && pageParams.length > 0) {
            int pageNum = pageParams[0];
            int pageSize = pageParams.length > 1 ? pageParams[1] : 10;
            PageHelper.startPage(pageNum, pageSize);
        }
        return d8gerAutoCodingMapper.queryD8gerAutoCodingMoList(d8gerAutoCodingMo);
    }

    /**
     * 根据id更新非null字段
     *
     * @param d8gerAutoCodingMo
     * @return
     */
    @Override
    public int updateSelectiveById(D8gerAutoCodingMo d8gerAutoCodingMo) {
        return d8gerAutoCodingMapper.updateByPrimaryKeySelective(d8gerAutoCodingMo);
    }

    /**
     * 根据id物理删除
     *
     * @param id
     * @return
     */
    @Override
    public <T extends Number> int delete(T id) {
        return d8gerAutoCodingMapper.deleteByPrimaryKey(id);
    }

}