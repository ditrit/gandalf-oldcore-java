package com.orness.gandalf.core.module.zeromqmodule.command.worker;

import org.zeromq.*;

import java.util.Random;

public abstract class WorkerZeroMQ {

    private static Random rand = new Random(System.nanoTime());
    private String connection;
    private ZContext context;
    protected ZMQ.Socket worker;
    protected String identity;

    public WorkerZeroMQ(String connection) {
        context = new ZContext();
        this.connection = connection;
        this.open();
    }

    public void open() {
        worker = context.createSocket(SocketType.REP);
        this.worker.connect(connection);
        identity = String.format("%04X-%04X", rand.nextInt(), rand.nextInt());
        worker.setIdentity(identity.getBytes(ZMQ.CHARSET));
        worker.connect(connection);

    }

    public void close() {
        worker.close();
        context.close();
    }

/*    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ZMsg msg = ZMsg.recvMsg(worker);
            ZFrame address = msg.pop();
            ZFrame command = msg.pop();
            ZFrame content = msg.pop();

            System.out.println("ID " + identity);
            System.out.println("REQ ADD " + address);
            System.out.println("REQ COMM " + command);
            System.out.println("REQ CONT " + content);

            //  Send reply back to client
            address.send(worker, ZFrame.REUSE + ZFrame.MORE);
            command.send(worker, ZFrame.REUSE + ZFrame.MORE);
            content.send(worker, ZFrame.REUSE);
            //worker.send("World".getBytes(), 0);
        }

    }

    public static void main(String[] args) {
        new Thread(new WorkerZeroMQ("tcp://localhost:5580")).start();
        new Thread(new WorkerZeroMQ("tcp://localhost:5580")).start();
    }*/
}
