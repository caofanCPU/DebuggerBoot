package com.xyz.caofancpu.trackingtime.service;

import com.xyz.caofancpu.trackingtime.model.D8SmartCodingMo;

import java.util.List;

/**
 * D8SmartCodingMo对应的Service接口定义
 *
 * @author 帝八哥
 */
public interface D8SmartCodingService {

    /**
     * 插入单条记录
     *
     * @param d8SmartCodingMo
     * @return
     */
    int add(D8SmartCodingMo d8SmartCodingMo);

    /**
     * 批量插入
     *
     * @param d8SmartCodingMoList
     * @return
     */
    int batchAdd(List<D8SmartCodingMo> d8SmartCodingMoList);

    /**
     * 查询列表, 如果携带分页参数则返回分页后的列表
     *
     * @param d8SmartCodingMo
     * @param pageParams      可选分页参数
     * @return
     */
    List<D8SmartCodingMo> queryD8SmartCodingMoList(D8SmartCodingMo d8SmartCodingMo, Integer... pageParams);

    /**
     * 根据id更新非null字段
     *
     * @param d8SmartCodingMo
     * @return
     */
    int updateSelectiveById(D8SmartCodingMo d8SmartCodingMo);

    /**
     * 批量根据id更新非null字段
     *
     * @param d8SmartCodingMoList
     * @return
     */
    int batchUpdateSelectiveById(List<D8SmartCodingMo> d8SmartCodingMoList);

    /**
     * 根据id物理删除
     *
     * @param id
     * @return
     */
    <T extends Number> int delete(T id);

}