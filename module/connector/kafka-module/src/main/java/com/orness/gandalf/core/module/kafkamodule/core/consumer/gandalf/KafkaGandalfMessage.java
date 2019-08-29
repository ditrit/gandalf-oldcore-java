package com.orness.gandalf.core.module.kafkamodule.core.consumer.gandalf;

public class KafkaGandalfMessage {

    private String topic;
    private String event;
    private String payload;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }


}
