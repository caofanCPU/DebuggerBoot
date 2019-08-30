package com.xyz.caofancpu.trackingtime.config;

import com.xyz.caofancpu.util.commonOperateUtils.enumType.AutoDispatchMyBatisEnumTypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * MyBatis配置
 *
 * @author caofanCPU
 */
//@Configuration
//@AutoConfigureAfter(MybatisAutoConfiguration.class)
@Slf4j
public class MyBatisConfig {

    @Resource
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 注册自定义枚举转换器
     */
    @PostConstruct
    public void customMybatisEnumTypeHandler() {
        TypeHandlerRegistry typeHandlerRegistry = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
        typeHandlerRegistry.setDefaultEnumTypeHandler(AutoDispatchMyBatisEnumTypeHandler.class);
        log.info("自定义枚举转换器注册成功!");
    }

}
