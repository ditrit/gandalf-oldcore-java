/*
package com.orness.gandalf.core.connector.connectorbusservice.other.kafka.communication.event;

import com.orness.gandalf.core.module.zeromqmodule.event.publisher.PublisherZeroMQ;

public class BusPublisherZeroMQ extends PublisherZeroMQ {

    public BusPublisherZeroMQ(String connection) {
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
*/
