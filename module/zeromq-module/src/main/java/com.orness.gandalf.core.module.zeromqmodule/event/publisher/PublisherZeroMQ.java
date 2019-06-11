package com.orness.gandalf.core.module.zeromqmodule.event.publisher;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ.Socket;

public class PublisherZeroMQ {

    private ZContext context;
    private Socket publisher;
    private String connection;

    public PublisherZeroMQ(String connection) {
        this.connection = connection;
        this.open();
    }

    public void open() {
        context = new ZContext();
        publisher = context.createSocket(SocketType.PUB);
        //publisher.bind(connection);
        publisher.connect(connection);
    }

    public void sendTopic(String topic) {
        this.publisher.sendMore(topic);

    }

    public void sendMessage(String message) {
        this.publisher.send(message);
    }

    public void close() {
        publisher.close();
        context.close();
    }
}
