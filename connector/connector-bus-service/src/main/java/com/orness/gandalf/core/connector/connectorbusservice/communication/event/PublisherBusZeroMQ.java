package com.orness.gandalf.core.connector.connectorbusservice.communication.event;

import com.orness.gandalf.core.module.zeromqmodule.event.publisher.PublisherZeroMQ;

public class PublisherBusZeroMQ extends PublisherZeroMQ {

    public PublisherBusZeroMQ(String connection) {
        super(connection);
    }

    private void sendTopic(String topic) {
        this.publisher.sendMore(topic);

    }

    private void sendMessage(String message) {
        this.publisher.send(message);
    }

    public void sendMessageTopic(String topic, String message) {
        this.sendTopic(topic);
        this.sendMessage(message);
    }
}
