package com.ditrit.gandalf.core.zeromqcore.event.domain;

import org.zeromq.ZMsg;

public class MessageEvent {

    private ZMsg message;
    private String topic;
    private String event;
    private String timeout;
    private String timestamp;
    private String payload;

    public ZMsg getMessage() {
        return message;
    }

    public void setMessage(ZMsg message) {
        this.message = message;
    }

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

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public MessageEvent(ZMsg event) {
        this.message = event.duplicate();
        this.topic = this.message.popString();
        this.event = this.message.popString();
        this.timeout = this.message.popString();
        this.timestamp = this.message.popString();
        this.payload = this.message.popString();
    }
}
