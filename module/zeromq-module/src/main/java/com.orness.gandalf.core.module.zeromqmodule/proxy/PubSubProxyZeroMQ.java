package com.orness.gandalf.core.module.zeromqmodule.proxy;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class PubSubProxyZeroMQ {

    public static Socket frontend;
    private String subscriberConnection;
    public static Socket backend;
    private String publisherConnection;
    private ZContext context;

    public PubSubProxyZeroMQ(String subscriberConnection, String publisherConnection) {
        this.publisherConnection = publisherConnection;
        this.subscriberConnection = subscriberConnection;
        this.open();
    }

    /*public static void main(String[] args) {
        new PubSubProxyZeroMQ("ipc://sub", "ipc://pub");
    }*/

    public void open() {

        context = new ZContext();

        //PUBLISHER ENDPOINT
        frontend = context.createSocket(SocketType.XSUB);
        System.out.println("PublisherZeroMQ binding to: " + publisherConnection);
        frontend.bind(publisherConnection);

        //SUBSCRIBER ENDPOINT
        backend = context.createSocket(SocketType.XPUB);
        System.out.println("SubscriberZeroMQ binding to: " + subscriberConnection);
        backend.bind(subscriberConnection);

        //Socket captureSocket = context.createSocket(SocketType.XPUB);
        //captureSocket.bind("ipc://capture");

        // Run the proxy until the user interrupts us
        ZMQ.proxy(frontend, backend, null);

        this.close();
    }


    public void close() {
        frontend.close();
        backend.close();
        context.destroy();
    }

}