package com.orness.gandalf.core.module.zeromqmodule.event.subscriber;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ.Socket;

public abstract class SubscriberZeroMQ {
    private String connection;
    private ZContext context;
    protected Socket subscriber;

    public SubscriberZeroMQ(String connection) {
        this.connection = connection;
        this.open();
        //this.run(null);
    }

    public void open() {
        context = new ZContext();
        subscriber = context.createSocket(SocketType.SUB);
        System.out.println(connection);
        subscriber.connect(connection);
        //subscriber.bind(connection);
        //subscriber.subscribe(topic.getBytes());
    }

    public void close() {
        subscriber.close();
        context.close();
    }

    /*public String getZeroMQMessage() {
        return this.subscriber.recvStr();
    }*/
}
