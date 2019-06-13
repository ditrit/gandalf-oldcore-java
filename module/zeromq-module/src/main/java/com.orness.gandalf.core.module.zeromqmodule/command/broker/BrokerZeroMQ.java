package com.orness.gandalf.core.module.zeromqmodule.command.broker;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class BrokerZeroMQ implements Runnable {

    public static ZMQ.Socket frontend;
    private String clientConnection;
    public static ZMQ.Socket backend;
    private String workerConnection;
    private ZContext context;

    public BrokerZeroMQ(String clientConnection, String workerConnection) {
        this.clientConnection = clientConnection;
        this.workerConnection = workerConnection;
        //this.open();
    }

    public void run() {

        context = new ZContext();

        // Client EndPoint
        frontend = context.createSocket(SocketType.ROUTER);
        System.out.println("ClientZeroMQ binding to: " + clientConnection);
        frontend.bind(clientConnection);

        // Worker EndPoint
        backend = context.createSocket(SocketType.DEALER);
        System.out.println("WorkerZeroMQ binding to: " + workerConnection);
        backend.bind(workerConnection);

        // Launch pool of worker threads, precise number is not critical
        /*for (int threadNbr = 0; threadNbr < 5; threadNbr++)
            new WorkerZeroMQ(context, workerConnection);*/
        //new Thread(new WorkerZeroMQ(context, workerConnection)).start();

        // Run the proxy until the user interrupts us
        //ZMQ.proxy(frontend, backend, null);
        this.broker();

        this.close();
    }

    public void broker() {
        //  Initialize poll set
        ZMQ.Poller items = context.createPoller(2);
        items.register(frontend, ZMQ.Poller.POLLIN);
        items.register(backend, ZMQ.Poller.POLLIN);

        boolean more = false;
        byte[] message;

        //  Switch messages between sockets
        while (!Thread.currentThread().isInterrupted()) {
            //  poll and memorize multipart detection
            items.poll();

            if (items.pollin(0)) {
                while (true) {
                    // receive message
                    message = frontend.recv(0);
                    more = frontend.hasReceiveMore();

                    // Broker it
                    backend.send(message, more ? ZMQ.SNDMORE : 0);
                    if (!more) {
                        break;
                    }
                }
            }

            if (items.pollin(1)) {
                while (true) {
                    // receive message
                    message = backend.recv(0);
                    more = backend.hasReceiveMore();
                    // Broker it
                    frontend.send(message, more ? ZMQ.SNDMORE : 0);
                    if (!more) {
                        break;
                    }
                }
            }
        }
    }

    public void close() {
        frontend.close();
        backend.close();
        context.destroy();
    }

    public static void main(String[] args) {
        new Thread(new BrokerZeroMQ("tcp://*:5570", "tcp://*:5580")).start();
    }
}
