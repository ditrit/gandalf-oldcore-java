package com.orness.gandalf.core.module.zeromqmodule.event.domain;

import org.zeromq.ZMQ;

public class ResponseEventZeroMQ {

    private String topic;
    private String typeEvent;
    private String event;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTypeEvent() {
        return typeEvent;
    }

    public void setTypeEvent(String typeEvent) {
        this.typeEvent = typeEvent;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public ResponseEventZeroMQ(ZMQ.Socket socket) {
        this.topic = socket.recvStr();
        this.typeEvent = socket.recvStr();
        this.event = socket.recvStr();
    }

    public ResponseEventZeroMQ(String topic, String typeEvent, String event) {
        this.topic = topic;
        this.typeEvent = typeEvent;
        this.event = event;
    }
}
