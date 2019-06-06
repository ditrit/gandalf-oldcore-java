package com.orness.gandalf.core.module.zeromqmodule.proxy;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

@Component
public class PubSubProxyZeroMQ implements DisposableBean, Runnable {

    private static Socket subscriberSocket;
    private String subscriberConnection;
    private static Socket publisherSocket;
    private String publisherConnection;
    private ZContext context;

    public PubSubProxyZeroMQ(String subscriberConnection, String publisherConnection) {
        this.publisherConnection = publisherConnection;
        this.subscriberConnection = subscriberConnection;
        this.open();
        this.run();
    }

    /*public static void main(String[] args) {
        new PubSubProxyZeroMQ("ipc://sub", "ipc://pub");
    }*/

    public void open() {

        context = new ZContext();

        //PUBLISHER ENDPOINT
        publisherSocket = context.createSocket(SocketType.XSUB);
        System.out.println("SubscriberZeroMQ binding to: " + subscriberConnection);
        publisherSocket.bind(subscriberConnection);

        //SUBSCRIBER ENDPOINT
        subscriberSocket = context.createSocket(SocketType.XPUB);
        System.out.println("PublisherZeroMQ binding to: " + publisherConnection);
        subscriberSocket.bind(publisherConnection);

        // Run the proxy until the user interrupts us
        //ZMQ.proxy(publisherSocket, subscriberSocket, null);

        //this.close();
    }


    public void close() {
        publisherSocket.close();
        subscriberSocket.close();
        context.destroy();
    }

    @Override
    public void run() {
        // Run the proxy until the user interrupts us
        ZMQ.proxy(publisherSocket, subscriberSocket, null);
    }

    @Override
    public void destroy() throws Exception {
        this.close();
    }
}