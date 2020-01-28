package com.xyz.caofancpu.trackingtime.service;

import com.xyz.caofancpu.trackingtime.model.UserInfoMo;
import com.xyz.caofancpu.trackingtime.view.UserInfoVo;

import java.util.List;

/**
 * UserInfoMo对应的Service接口定义
 *
 * @author Power+
 */
public interface UserInfoService {

    /**
     * 插入单条记录
     *
     * @param userInfoMo
     * @return
     */
    int add(UserInfoMo userInfoMo);

    /**
     * 批量插入
     *
     * @param userInfoMoList
     * @return
     */
    int batchAdd(List<UserInfoMo> userInfoMoList);

    /**
     * 查询列表, 如果携带分页参数则返回分页后的列表
     *
     * @param userInfoMo
     * @param pageParams 可选分页参数
     * @return
     */
    List<UserInfoVo> queryUserInfoMoList(UserInfoMo userInfoMo, Integer... pageParams);

    /**
     * 根据id更新非null字段
     *
     * @param userInfoMo
     * @return
     */
    int updateSelectiveById(UserInfoMo userInfoMo);

    /**
     * 批量根据id更新非null字段
     *
     * @param userInfoMoList
     * @return
     */
    int batchUpdateSelectiveById(List<UserInfoMo> userInfoMoList);

    /**
     * 根据id物理删除
     *
     * @param id
     * @return
     */
    <T extends Number> int delete(T id);

}