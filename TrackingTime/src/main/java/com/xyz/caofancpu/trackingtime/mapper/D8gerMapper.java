package com.xyz.caofancpu.trackingtime.mapper;

import com.xyz.caofancpu.trackingtime.mapper.example.D8gerExample;
import com.xyz.caofancpu.trackingtime.model.D8gerMo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * D8gerMo对应的Mapper
 *
 * @author 帝八哥
 */
@Mapper
public interface D8gerMapper {

    /**
     * 根据条件查询列表
     *
     * @param d8gerExample
     * @return
     */
    List<D8gerMo> selectByExample(D8gerExample d8gerExample);

    /**
     * 批量更新, 根据主键更新非null字段
     *
     * @param d8gerMoList
     * @return
     */
    int updateBatchByPrimaryKeySelective(List<D8gerMo> d8gerMoList);

    /**
     * 根据条件更新非null字段
     *
     * @param d8gerMo
     * @param d8gerExample
     * @return
     */
    int updateByExampleSelective(@Param("record") D8gerMo d8gerMo, @Param("example") D8gerExample d8gerExample);

    /**
     * 根据条件删除记录
     *
     * @param d8gerExample
     * @return
     */
    int deleteByExample(D8gerExample d8gerExample);

    /**
     * 根据条件统计记录
     *
     * @param d8gerExample
     * @return 记录条数
     */
    int countByExample(D8gerExample d8gerExample);

    /**
     * 增加单条记录, 并为入参设置ID
     *
     * @param d8gerMo
     * @return
     */
    int insertWithId(D8gerMo d8gerMo);

    /**
     * 批量增加记录, 并为入参设置ID
     * 注意: `id` | `createTime` | `updateTime`字段将被忽略, 以数据库为准
     *
     * @param d8gerMoList
     * @return
     */
    int insertBatchWithId(List<D8gerMo> d8gerMoList);

    /**
     * D8ger列表查询
     *
     * @param d8gerMo
     * @return
     */
    List<D8gerMo> queryD8gerMoList(D8gerMo d8gerMo);

    /**
     * 根据ID查询对象
     *
     * @param id
     * @return
     */
    <T extends Number> D8gerMo selectByPrimaryKey(T id);

    /**
     * 根据主键只更新非null字段
     *
     * @param d8gerMo
     * @return
     */
    int updateByPrimaryKeySelective(D8gerMo d8gerMo);

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
     * @param d8gerExample
     * @return
     */
    D8gerMo selectOneByExample(D8gerExample d8gerExample);

    /**
     * 增加单条非空字段记录, 并为入参设置ID
     *
     * @param d8gerMo
     * @return
     */
    int insertSelectiveWithId(D8gerMo d8gerMo);

}