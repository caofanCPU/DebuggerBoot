package com.xyz.caofancpu.message.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by caofanCPU on 2018/8/3.
 */
@Configuration
@ConfigurationProperties(prefix = "kafka") //声明前缀
@PropertySource(value = "classpath:kafka.properties") // 声明文件访问路径
public class KafkaProperty {
    
    private Map<String, String> topicMap = new HashMap<>();
    
    public Map<String, String> getTopicMap() {
        return topicMap;
    }
    
    public void setTopicMap(Map<String, String> topicMap) {
        this.topicMap = topicMap;
    }
}
