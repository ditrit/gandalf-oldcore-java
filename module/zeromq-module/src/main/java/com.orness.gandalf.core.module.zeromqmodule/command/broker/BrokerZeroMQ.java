package com.orness.gandalf.core.module.zeromqmodule.command.broker;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class BrokerZeroMQ {

    public static ZMQ.Socket frontend;
    private String clientConnection;
    public static ZMQ.Socket backend;
    private String workerConnection;
    private ZContext context;

    public BrokerZeroMQ(String clientConnection, String workerConnection) {
        this.clientConnection = clientConnection;
        this.workerConnection = workerConnection;
        this.open();
    }

    public void open() {

        this.context = new ZContext();

        // Client EndPoint
        this.frontend = this.context.createSocket(SocketType.ROUTER);
        System.out.println("BrokerClientZeroMQ binding to: " + this.clientConnection);
        this.frontend.bind(this.clientConnection);

        // Worker EndPoint
        this.backend = this.context.createSocket(SocketType.DEALER);
        System.out.println("BrokerWorkerZeroMQ binding to: " + this.workerConnection);
        this.backend.bind(this.workerConnection);

        this.broker();

        this.close();
    }

    public void broker() {
        //  Initialize poll set
        ZMQ.Poller items = context.createPoller(2);
        items.register(this.frontend, ZMQ.Poller.POLLIN);
        items.register(this.backend, ZMQ.Poller.POLLIN);

        boolean more = false;
        byte[] message;

        //  Switch messages between sockets
        while (!Thread.currentThread().isInterrupted()) {
            //  poll and memorize multipart detection
            items.poll();

            if (items.pollin(0)) {
                while (true) {
                    // receive message
                    message = this.frontend.recv(0);
                    more = this.frontend.hasReceiveMore();

                    // Broker it
                    this.backend.send(message, more ? ZMQ.SNDMORE : 0);
                    if (!more) {
                        break;
                    }
                }
            }

            if (items.pollin(1)) {
                while (true) {
                    // receive message
                    message = this.backend.recv(0);
                    more = this.backend.hasReceiveMore();
                    // Broker it
                    this.frontend.send(message, more ? ZMQ.SNDMORE : 0);
                    if (!more) {
                        break;
                    }
                }
            }
        }
    }

    public void close() {
        this.frontend.close();
        this.backend.close();
        this.context.destroy();
    }

}
