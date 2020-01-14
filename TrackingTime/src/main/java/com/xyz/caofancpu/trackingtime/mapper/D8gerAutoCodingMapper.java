package com.xyz.caofancpu.trackingtime.mapper;

import com.xyz.caofancpu.trackingtime.mapper.example.D8gerAutoCodingExample;
import com.xyz.caofancpu.trackingtime.model.D8gerAutoCodingMo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * D8gerAutoCodingMo对应的Mapper
 *
 * @author caofanCPU
 */
@Mapper
public interface D8gerAutoCodingMapper {

    /**
     * 根据条件查询列表
     *
     * @param d8gerAutoCodingExample
     * @return
     */
    List<D8gerAutoCodingMo> selectByExample(D8gerAutoCodingExample d8gerAutoCodingExample);

    /**
     * 批量更新, 根据主键更新非null字段
     *
     * @param d8gerAutoCodingMoList
     * @return
     */
    int updateBatchByPrimaryKeySelective(List<D8gerAutoCodingMo> d8gerAutoCodingMoList);

    /**
     * 根据条件更新非null字段
     *
     * @param d8gerAutoCodingMo
     * @param d8gerAutoCodingExample
     * @return
     */
    int updateByExampleSelective(@Param("record") D8gerAutoCodingMo d8gerAutoCodingMo, @Param("example") D8gerAutoCodingExample d8gerAutoCodingExample);

    /**
     * 根据条件删除记录
     *
     * @param d8gerAutoCodingExample
     * @return
     */
    int deleteByExample(D8gerAutoCodingExample d8gerAutoCodingExample);

    /**
     * 根据条件统计记录
     *
     * @param d8gerAutoCodingExample
     * @return 记录条数
     */
    int countByExample(D8gerAutoCodingExample d8gerAutoCodingExample);

    /**
     * 增加单条记录, 并为入参设置ID
     *
     * @param d8gerAutoCodingMo
     * @return
     */
    int insertWithId(D8gerAutoCodingMo d8gerAutoCodingMo);

    /**
     * 批量增加记录, 并为入参设置ID
     *
     * @param d8gerAutoCodingMoList
     * @return
     */
    int insertBatchWithId(List<D8gerAutoCodingMo> d8gerAutoCodingMoList);

    /**
     * D8gerAutoCoding列表查询
     *
     * @param d8gerAutoCodingMo
     * @return
     */
    List<D8gerAutoCodingMo> queryD8gerAutoCodingMoList(D8gerAutoCodingMo d8gerAutoCodingMo);

    /**
     * 根据ID查询对象
     *
     * @param id
     * @return
     */
    <T extends Number> D8gerAutoCodingMo selectByPrimaryKey(T id);

    /**
     * 根据主键只更新非null字段
     *
     * @param d8gerAutoCodingMo
     * @return
     */
    int updateByPrimaryKeySelective(D8gerAutoCodingMo d8gerAutoCodingMo);

    /**
     * 根据ID删除记录
     *
     * @param id
     * @return
     */
    <T extends Number> int deleteByPrimaryKey(T id);
}