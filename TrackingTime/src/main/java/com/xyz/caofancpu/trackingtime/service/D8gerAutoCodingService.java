package com.xyz.caofancpu.trackingtime.service;

import com.xyz.caofancpu.trackingtime.model.D8gerAutoCodingMo;

import java.util.List;

/**
 * D8gerAutoCodingMo对应的Service接口定义
 *
 * @author 曹繁
 */
public interface D8gerAutoCodingService {

    /**
     * 插入单条记录
     *
     * @param d8gerAutoCodingMo
     * @return
     */
    int add(D8gerAutoCodingMo d8gerAutoCodingMo);

    /**
     * 批量插入
     *
     * @param d8gerAutoCodingMoList
     * @return
     */
    int batchAdd(List<D8gerAutoCodingMo> d8gerAutoCodingMoList);

    /**
     * 查询列表, 如果携带分页参数则返回分页后的列表
     *
     * @param d8gerAutoCodingMo
     * @param pageParams        可选分页参数
     * @return
     */
    List<D8gerAutoCodingMo> queryD8gerAutoCodingMoList(D8gerAutoCodingMo d8gerAutoCodingMo, Integer... pageParams);

    /**
     * 根据id更新非null字段
     *
     * @param d8gerAutoCodingMo
     * @return
     */
    int updateSelectiveById(D8gerAutoCodingMo d8gerAutoCodingMo);

    /**
     * 根据id物理删除
     *
     * @param id
     * @return
     */
    <T extends Number> int delete(T id);

}