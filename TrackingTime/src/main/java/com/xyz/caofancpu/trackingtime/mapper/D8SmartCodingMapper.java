package com.xyz.caofancpu.trackingtime.mapper;

import com.xyz.caofancpu.trackingtime.mapper.example.D8SmartCodingExample;
import com.xyz.caofancpu.trackingtime.model.D8SmartCodingMo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * D8SmartCodingMo对应的Mapper
 *
 * @author 帝八哥
 */
@Mapper
public interface D8SmartCodingMapper {

    /**
     * 根据条件查询列表
     *
     * @param d8SmartCodingExample
     * @return
     */
    List<D8SmartCodingMo> selectByExample(D8SmartCodingExample d8SmartCodingExample);

    /**
     * 批量更新, 根据主键更新非null字段
     *
     * @param d8SmartCodingMoList
     * @return
     */
    int updateBatchByPrimaryKeySelective(List<D8SmartCodingMo> d8SmartCodingMoList);

    /**
     * 根据条件更新非null字段
     *
     * @param d8SmartCodingMo
     * @param d8SmartCodingExample
     * @return
     */
    int updateByExampleSelective(@Param("record") D8SmartCodingMo d8SmartCodingMo, @Param("example") D8SmartCodingExample d8SmartCodingExample);

    /**
     * 根据条件删除记录
     *
     * @param d8SmartCodingExample
     * @return
     */
    int deleteByExample(D8SmartCodingExample d8SmartCodingExample);

    /**
     * 根据条件统计记录
     *
     * @param d8SmartCodingExample
     * @return 记录条数
     */
    int countByExample(D8SmartCodingExample d8SmartCodingExample);

    /**
     * 增加单条记录, 并为入参设置ID
     *
     * @param d8SmartCodingMo
     * @return
     */
    int insertWithId(D8SmartCodingMo d8SmartCodingMo);

    /**
     * 批量增加记录, 并为入参设置ID
     *
     * @param d8SmartCodingMoList
     * @return
     */
    int insertBatchWithId(List<D8SmartCodingMo> d8SmartCodingMoList);

    /**
     * D8SmartCoding列表查询
     *
     * @param d8SmartCodingMo
     * @return
     */
    List<D8SmartCodingMo> queryD8SmartCodingMoList(D8SmartCodingMo d8SmartCodingMo);

    /**
     * 根据ID查询对象
     *
     * @param id
     * @return
     */
    <T extends Number> D8SmartCodingMo selectByPrimaryKey(T id);

    /**
     * 根据主键只更新非null字段
     *
     * @param d8SmartCodingMo
     * @return
     */
    int updateByPrimaryKeySelective(D8SmartCodingMo d8SmartCodingMo);

    /**
     * 根据ID删除记录
     *
     * @param id
     * @return
     */
    <T extends Number> int deleteByPrimaryKey(T id);
}