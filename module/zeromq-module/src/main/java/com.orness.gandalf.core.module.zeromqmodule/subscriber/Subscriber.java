package com.orness.gandalf.core.module.zeromqmodule.subscriber;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZThread;
import org.zeromq.ZMQ.Socket;

public class Subscriber implements ZThread.IDetachedRunnable {
    private String connection;
    private ZContext context;
    private Socket subscriber;


    /*public static void main(String... args) {
        new Subscriber("ipc://sub").run(null);
    }*/


    public Subscriber(String connection) {
        this.connection = connection;
        this.run(null);
    }


    @Override
    public void run(Object[] args) {
        open();

        while (!Thread.currentThread().isInterrupted()) {
            String topic = subscriber.recvStr();
            System.out.println(topic);
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
        String topic = "test";
        subscriber.subscribe(topic.getBytes());
    }


    void close() {
        subscriber.close();
        context.close();
    }
}
