package com.xyz.caofancpu.message.kafka;

/**
 *
 */
public class KafkaMessage {

    private String topic;

    /**
     * 数据对象
     */
    private Object data;

    public KafkaMessage(String topic, Object data) {
        this.topic = topic;
        this.data = data;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Object getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
