package com.orness.gandalf.core.module.zeromqmodule.subscriber;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZThread;
import org.zeromq.ZMQ.Socket;

public class SubscriberZeroMQ implements ZThread.IDetachedRunnable {
    private String connection;
    private String topic;
    private ZContext context;
    private Socket subscriber;


    /*public static void main(String... args) {
        new SubscriberZeroMQ("ipc://sub").run(null);
    }*/


    public SubscriberZeroMQ(String connection, String topic) {
        this.connection = connection;
        this.topic = topic;
        this.run(null);
    }


    @Override
    public void run(Object[] args) {
        open();

        while (!Thread.currentThread().isInterrupted()) {
            String header = subscriber.recvStr();
            System.out.println(header);
            String content = subscriber.recvStr();
            System.out.println(content);
        }
        close();
    }



    private void open() {
        context = new ZContext();
        subscriber = context.createSocket(SocketType.SUB);

        subscriber.connect(connection);
        //subscriber.bind(connection);
        subscriber.subscribe(topic.getBytes());
    }


    void close() {
        subscriber.close();
        context.close();
    }
}
