package com.xyz.caofancpu.message.kafka;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by caofanCPU on 2018/8/3.
 */
@Configuration
@ConfigurationProperties(prefix = "kafka") //声明前缀
@PropertySource(value = "classpath:kafka.properties") // 声明文件访问路径
@Scope(value = "singleton")
public class KafkaProperty {
    
    private Map<String, String> topicMap = new ConcurrentHashMap<>();
    
    private String[] topics = new String[]{String.valueOf(new Object().hashCode())};
    
    public Map<String, String> getTopicMap() {
        return topicMap;
    }
    
    public void setTopicMap(Map<String, String> topicMap) {
        this.topicMap = topicMap;
    }
    
    public String[] getTopics() {
        return topics;
    }
    
    public void setTopics(String[] topics) {
        this.topics = topics;
    }
}
