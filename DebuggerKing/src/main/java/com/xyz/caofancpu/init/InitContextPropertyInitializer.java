package com.xyz.caofancpu.init;

import com.xyz.caofancpu.mapper.SysDictMapper;
import com.xyz.caofancpu.util.streamOperateUtils.CollectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 读取自定义配置变量, 设置为全局
 *
 * @author caofanCPU
 */
@Configuration("initContextPropertyInitializer")
public class InitContextPropertyInitializer {

    public static final String PROPERTY_KEY = "name";
    public static final String PROPERTY_VALUE = "code";
    public static final String DB_PROPERTY_SOURCE = "dbPropertySource";
    public static final String YAML_PROPERTY_SOURCE = "yamlPropertySource";
    /**
     * 约定大于配置, properties文件配置
     */
    @Deprecated
    public static final String PROPERTIES_PROPERTY_SOURCE = "propertiesPropertySource";
    private static final Logger logger = LoggerFactory.getLogger(InitContextPropertyInitializer.class);
    @Autowired
    private transient SysDictMapper sysDictMapper;


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
    public void execute() {
        // 1.读取数据库配置
        readDbProperty();
        // 2.读取yaml配置文件变量
        readYamlProperty();
    }

    /**
     * 读取数据库配置表, 设置为全局
     */
    public void readDbProperty() {
        Properties dbProperty = new Properties();
        PropertiesPropertySource dbPropertySource = new PropertiesPropertySource(DB_PROPERTY_SOURCE, dbProperty);
        try {
            logger.info("读取初始化数据库配置变量...");
            List<Map<String, Object>> initDbPropertyList = sysDictMapper.getInitSysDictList();
            initDbPropertyList.forEach(item -> dbProperty.put(item.get(PROPERTY_KEY), item.get(PROPERTY_VALUE)));
            // 在队尾添加数据库的配置属性，即最后使用数据库配置属性
            configurableEnvironment.getPropertySources().addLast(dbPropertySource);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return;
        }
        logger.info("完成数据库配置初始化!");
    }

    /**
     * 从.yaml配置文件读取配置属性, 设置为全局
     * 注意: ClassPathResource加载的路径, 直接填写相对于resource的目录即可, 开头不需要'/'
     */
    public void readYamlProperty() {
        try {
            logger.info("读取初始化yaml配置变量...");
            List<PropertySource<?>> propertySourceList = new YamlPropertySourceLoader().load(YAML_PROPERTY_SOURCE, new ClassPathResource("config/config-demo.yml"));
            if (CollectionUtil.isNotEmpty(propertySourceList)) {
                configurableEnvironment.getPropertySources().addFirst(propertySourceList.get(0));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return;
        }
        logger.info("完成yaml配置变量初始化!");
    }
}
