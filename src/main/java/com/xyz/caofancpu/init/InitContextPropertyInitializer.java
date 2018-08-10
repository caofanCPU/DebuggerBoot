package com.xyz.caofancpu.init;

import com.xyz.caofancpu.service.SysDictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库读取配置变量
 * @author caofanCPU
 * @date 2018-08-03
 */
@Configuration("initContextPropertyInitializer")
public class InitContextPropertyInitializer {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(InitContextPropertyInitializer.class);
    
    public static final String PROPERTY_KEY = "name";
    
    public static final String PROPERTY_VALUE = "code";
    
    public static final String DB_PROPERTY_SOURCE = "dbPropertySource";
    
    @Autowired
    private transient SysDictService sysDictService;
    
    
    /**
     * 配置变量说明
     * spring boot指定了配置变量的访问优先级，【变量组】访问由高至低如下:
     * <p>
     * [1] server.ports
     * [2] servletConfigInitParams
     * [3] servletContextInitParams
     * [4] systemProperties
     * [5] systemEnvironment
     * [6] random
     * [7] applicationConfig: [classpath:/application-{环境：dev/alpha/beta/product}.properties]
     * [8] applicationConfig: [classpath:/application.properties]
     * [9] springCloudClientHostInfo
     * [10] {自定义属性配置文件:加载为@Bean}
     * [11] defaultProperties
     * <p>
     * 为方便调试，采用配置文件覆盖数据库，因而：
     * [12] {自定义数据库配置变量}
     */
    @Resource
    private transient ConfigurableEnvironment configurableEnvironment;
    
    @PostConstruct
    public void excute() {
        readDbProperty();
    }
    
    /**
     * 读取数据库配置表
     */
    public void readDbProperty() {
        Properties dbProperty = new Properties();
        PropertiesPropertySource dbPropertySource = new PropertiesPropertySource(DB_PROPERTY_SOURCE, dbProperty);
        try {
            LOGGER.info("读取初始化数据库配置变量");
            List<Map<String, Object>> initDbPropertyList = sysDictService.getInitSysDictList();
            initDbPropertyList.forEach(item -> dbProperty.put(item.get(PROPERTY_KEY), item.get(PROPERTY_VALUE)));
            // 在队尾添加数据库的配置属性，即最后使用数据库配置属性
            configurableEnvironment.getPropertySources().addLast(dbPropertySource);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
