package com.xyz.caofancpu.trackingtime.mapper;

import com.xyz.caofancpu.trackingtime.mapper.example.UserInfoExample;
import com.xyz.caofancpu.trackingtime.model.UserInfoMo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * UserInfoMo对应的Mapper
 *
 * @author Power+
 */
@Mapper
public interface UserInfoMapper {

    /**
     * 根据条件查询列表
     *
     * @param userInfoExample
     * @return
     */
    List<UserInfoMo> selectByExample(UserInfoExample userInfoExample);

    /**
     * 批量更新, 根据主键更新非null字段
     *
     * @param userInfoMoList
     * @return
     */
    int updateBatchByPrimaryKeySelective(List<UserInfoMo> userInfoMoList);

    /**
     * 根据条件更新非null字段
     *
     * @param userInfoMo
     * @param userInfoExample
     * @return
     */
    int updateByExampleSelective(@Param("record") UserInfoMo userInfoMo, @Param("example") UserInfoExample userInfoExample);

    /**
     * 根据条件删除记录
     *
     * @param userInfoExample
     * @return
     */
    int deleteByExample(UserInfoExample userInfoExample);

    /**
     * 根据条件统计记录
     *
     * @param userInfoExample
     * @return 记录条数
     */
    int countByExample(UserInfoExample userInfoExample);

    /**
     * 增加单条记录, 并为入参设置ID
     *
     * @param userInfoMo
     * @return
     */
    int insertWithId(UserInfoMo userInfoMo);

    /**
     * 批量增加记录, 并为入参设置ID
     *
     * @param userInfoMoList
     * @return
     */
    int insertBatchWithId(List<UserInfoMo> userInfoMoList);

    /**
     * UserInfo列表查询
     *
     * @param userInfoMo
     * @return
     */
    List<UserInfoMo> queryUserInfoMoList(UserInfoMo userInfoMo);

    /**
     * 根据ID查询对象
     *
     * @param id
     * @return
     */
    <T extends Number> UserInfoMo selectByPrimaryKey(T id);

    /**
     * 根据主键只更新非null字段
     *
     * @param userInfoMo
     * @return
     */
    int updateByPrimaryKeySelective(UserInfoMo userInfoMo);

    /**
     * 根据ID删除记录
     *
     * @param id
     * @return
     */
    <T extends Number> int deleteByPrimaryKey(T id);

    /**
     * 根据条件查询单个对象
     *
     * @param userInfoExample
     * @return
     */
    UserInfoMo selectOneByExample(UserInfoExample userInfoExample);

    /**
     * 增加单条非空字段记录, 并为入参设置ID
     *
     * @param userInfoMo
     * @return
     */
    int insertSelectiveWithId(UserInfoMo userInfoMo);

}