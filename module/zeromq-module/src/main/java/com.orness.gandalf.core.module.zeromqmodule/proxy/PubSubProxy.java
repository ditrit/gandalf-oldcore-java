package com.orness.gandalf.core.module.zeromqmodule.proxy;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

public class PubSubProxy {

    private static Socket subscriberSocket;
    private String subscriberConnection;
    private static Socket publisherSocket;
    private String publisherConnection;
    private ZContext context;

    public PubSubProxy(String subscriberConnection, String publisherConnection) {
        this.publisherConnection = publisherConnection;
        this.subscriberConnection = subscriberConnection;
        this.open();
    }

    /*public static void main(String[] args) {
        new PubSubProxy("ipc://sub", "ipc://pub");
    }*/

    public void open() {

        context = new ZContext();

        //PUBLISHER ENDPOINT
        publisherSocket = context.createSocket(SocketType.XSUB);
        System.out.println("Subscriber binding to: " + subscriberConnection);
        publisherSocket.bind(subscriberConnection);

        //SUBSCRIBER ENDPOINT
        subscriberSocket = context.createSocket(SocketType.XPUB);
        System.out.println("Publisher binding to: " + publisherConnection);
        subscriberSocket.bind(publisherConnection);

        // Run the proxy until the user interrupts us
        ZMQ.proxy(publisherSocket, subscriberSocket, null);

        this.close();
    }


    public void close() {
        publisherSocket.close();
        subscriberSocket.close();
        context.destroy();
    }
}