package com.orness.gandalf.core.module.zeromqmodule.command.client;

import org.zeromq.*;

import java.util.Random;

public abstract class ClientZeroMQ {

    private static Random rand = new Random(System.nanoTime());
    private String connection;
    private ZContext context;
    protected ZMQ.Socket client;
    protected String identity;

    public ClientZeroMQ(String connection) {
        this.connection = connection;
        this.open();
    }

    public void open() {
        context = new ZContext();
        client = context.createSocket(SocketType.REQ);
        //publisher.bind(connection);
        //  Set random identity to make tracing easier
        identity = String.format("%04X-%04X", rand.nextInt(), rand.nextInt());
        client.setIdentity(identity.getBytes(ZMQ.CHARSET));
        client.connect(connection);
    }

    public void close() {
        client.close();
        context.close();
    }

/*    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            client.sendMore(identity);
            client.sendMore("command");
            client.send("toto", 0);

            ZMsg msg = ZMsg.recvMsg(client);
            ZFrame address = msg.pop();
            ZFrame command = msg.pop();
            ZFrame content = msg.pop();

            System.out.println("ID " + identity);
            System.out.println("REP ADD " + address);
            System.out.println("REP COMM " + command);
            System.out.println("REP CONT " + content);
            msg.destroy();
        }
    }

    public static void main(String[] args) {
        new Thread(new ClientZeroMQ("tcp://localhost:5570")).start();
        new Thread(new ClientZeroMQ("tcp://localhost:5570")).start();
    }*/
}
