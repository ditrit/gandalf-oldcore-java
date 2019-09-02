package com.orness.gandalf.core.module.zeromqcore.event.domain;

import org.zeromq.ZMsg;

public class MessageEvent {

    private ZMsg message;
    private String topic;
    private String event;
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

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public MessageEvent(ZMsg event) {
        this.message = event.duplicate();
        this.topic = event.popString();
        this.event = event.popString();
        this.payload = event.popString();
    }
}