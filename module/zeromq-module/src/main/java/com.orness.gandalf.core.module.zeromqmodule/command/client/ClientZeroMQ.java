package com.orness.gandalf.core.module.zeromqmodule.command.client;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.Random;

public class ClientZeroMQ {

    private static Random rand = new Random(System.nanoTime());
    private String connection;
    private ZContext context;
    private ZMQ.Socket client;
    private ZMQ.Poller poller;
    private String identity;

    public ClientZeroMQ(String connection) {
        this.connection = connection;
        this.open();
    }

    /*public ZMQ.Socket getClient() {
        return client;
    }*/

    public void open() {
        context = new ZContext();
        client = context.createSocket(SocketType.DEALER);
        //publisher.bind(connection);
        //  Set random identity to make tracing easier
        identity = String.format("%04X-%04X", rand.nextInt(), rand.nextInt());
        client.setIdentity(identity.getBytes(ZMQ.CHARSET));
        client.connect(connection);

        poller = context.createPoller(1);
        poller.register(client, ZMQ.Poller.POLLIN);
    }

    public void close() {
        client.close();
        context.close();
    }

    public void sendMessage() {
        for (int centitick = 0; centitick < 100; centitick++) {
            poller.poll(10);
            if (poller.pollin(0)) {
                ZMsg msg = ZMsg.recvMsg(client);
                msg.getLast().print(identity);
                msg.destroy();
            }
        }
        client.send("request #%d", 0);
    }

/*    @Override
    public void run()
    {
        try (ZContext ctx = new ZContext()) {
            ZMQ.Socket client = ctx.createSocket(SocketType.DEALER);

            //  Set random identity to make tracing easier
            String identity = String.format(
                    "%04X-%04X", rand.nextInt(), rand.nextInt()
            );
            client.setIdentity(identity.getBytes(ZMQ.CHARSET));
            client.connect("tcp://localhost:5570");

            ZMQ.Poller poller = ctx.createPoller(1);
            poller.register(client, ZMQ.Poller.POLLIN);

            int requestNbr = 0;
            while (!Thread.currentThread().isInterrupted()) {
                //  Tick once per second, pulling in arriving messages
                for (int centitick = 0; centitick < 100; centitick++) {
                    poller.poll(10);
                    if (poller.pollin(0)) {
                        ZMsg msg = ZMsg.recvMsg(client);
                        msg.getLast().print(identity);
                        msg.destroy();
                    }
                }
                client.send(String.format("request #%d", ++requestNbr), 0);
            }
        }
    }*/
}
