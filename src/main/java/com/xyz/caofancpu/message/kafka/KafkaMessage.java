package com.xyz.caofancpu.message.kafka;

/**
 * @auth caofaCPU
 * @date 2018-08-03
 */
public class KafkaMessage {
    
    private String topic;
    
    private String data;
    
    public KafkaMessage(String topic, String data) {
        this.topic = topic;
        this.data = data;
    }
    
    public String getTopic() {
        return topic;
    }
    
    public void setTopic(String topic) {
        this.topic = topic;
    }
    
    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
}
