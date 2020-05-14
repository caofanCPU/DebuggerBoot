package com.xyz.caofancpu.init;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 从配置文件中读取配置
 *
 * @author D8GER
 */
@Configuration
@ConfigurationProperties(prefix = "kafka")
@PropertySource(value = "classpath:kafka.properties")
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
