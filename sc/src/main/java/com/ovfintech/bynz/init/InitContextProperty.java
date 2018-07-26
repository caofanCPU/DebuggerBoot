package com.ovfintech.bynz.init;

import com.ovfintech.bynz.service.SysDictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 数据库读取配置变量
 */
@Component
public class InitContextProperty {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(InitContextProperty.class);
    
    public static final String PROPERTY_KEY = "name";
    
    public static final String PROPERTY_VALUE = "code";
    
    public static final String DB_PROPERTY_SOURCE = "dbPropertySource";
    
    @Autowired
    private transient SysDictService sysDictService;
    
    @Resource
    private transient ConfigurableEnvironment configurableEnvironment;
    
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
            // 在队首添加数据库的配置属性，即优先使用数据库配置属性
            configurableEnvironment.getPropertySources().addFirst(dbPropertySource);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }
}
