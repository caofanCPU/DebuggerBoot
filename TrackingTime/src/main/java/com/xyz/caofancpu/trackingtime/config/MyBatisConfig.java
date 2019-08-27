package com.xyz.caofancpu.trackingtime.config;

import com.xyz.caofancpu.trackingtime.service.configValue.CommonConfigValueService;
import com.xyz.caofancpu.util.commonOperateUtils.enumType.AutoDispatchMyBatisEnumTypeHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * MyBatis配置
 *
 * @author caofanCPU
 */
//@Configuration
@Slf4j
public class MyBatisConfig {

    @Autowired
    private CommonConfigValueService commonConfigValueService;

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource)
            throws Exception {
        SqlSessionFactory sqlSessionFactory = null;
        try {
            SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
            factory.setDataSource(dataSource);
            // 1.读取mybatis配置文件及mapper.xml文件
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            factory.setConfigLocation(resolver.getResource("待定"));
            factory.setMapperLocations(resolver.getResources("待定"));
            sqlSessionFactory = factory.getObject();

            // 2.注册自定义枚举转换器, 设置为默认枚举转换器
            TypeHandlerRegistry typeHandlerRegistry = sqlSessionFactory.getConfiguration().getTypeHandlerRegistry();
            typeHandlerRegistry.setDefaultEnumTypeHandler(AutoDispatchMyBatisEnumTypeHandler.class);
        } catch (Exception e) {
            log.error("创建SqlSessionFactory失败, 原因: {}", e);
        }
        return sqlSessionFactory;
    }

}
