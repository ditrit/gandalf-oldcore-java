package com.orness.gandalf.core.module.zeromqmodule.command.broker;

import com.orness.gandalf.core.module.zeromqmodule.command.worker.WorkerZeroMQ;
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
        for (int threadNbr = 0; threadNbr < 5; threadNbr++)
            new Thread(new WorkerZeroMQ(context)).start();

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
