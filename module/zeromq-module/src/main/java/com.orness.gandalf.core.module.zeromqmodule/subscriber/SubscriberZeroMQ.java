package com.orness.gandalf.core.module.zeromqmodule.subscriber;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ.Socket;

public class SubscriberZeroMQ {
    private String connection;
    private ZContext context;
    private Socket subscriber;

    public SubscriberZeroMQ(String connection) {
        this.connection = connection;
        //this.run(null);
    }

    public Socket getSubscriber() {
        return subscriber;
    }

    public void open(String topic) {
        context = new ZContext();
        subscriber = context.createSocket(SocketType.SUB);

        subscriber.connect(connection);
        //subscriber.bind(connection);
        subscriber.subscribe(topic.getBytes());
    }

    public void close() {
        subscriber.close();
        context.close();
    }
}
