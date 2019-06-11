package com.orness.gandalf.core.module.zeromqmodule.command.worker;

import org.zeromq.*;

import java.util.Random;

public class WorkerZeroMQ {

    private static Random rand = new Random(System.nanoTime());


    private String connection;
    private ZContext context;
    private ZMQ.Socket worker;

    public WorkerZeroMQ(ZContext context, String connection) {
        this.context = context;
        this.connection = connection;
        //this.open(topic);
        //this.run(null);
    }

    /*public ZMQ.Socket getWorker() {
        return worker;
    }*/

    public void open(String topic) {
        this.worker = context.createSocket(SocketType.DEALER);
        this.worker.connect(connection);
        //subscriber.bind(connection);
        //subscriber.subscribe(topic.getBytes());
    }

    public void close() {
        worker.close();
        context.close();
    }

    public void reply() {
        ZMsg msg = ZMsg.recvMsg(worker);
        ZFrame address = msg.pop();
        ZFrame content = msg.pop();
        assert (content != null);
        msg.destroy();

        address.send(worker, ZFrame.REUSE + ZFrame.MORE);
        content.send(worker, ZFrame.REUSE);

        address.destroy();
        content.destroy();
    }

/*    @Override
    public void run()
    {
        ZMQ.Socket worker = context.createSocket(SocketType.DEALER);
        worker.connect("inproc://backend");

        while (!Thread.currentThread().isInterrupted()) {
            //  The DEALER socket gives us the address envelope and message
            ZMsg msg = ZMsg.recvMsg(worker);
            ZFrame address = msg.pop();
            ZFrame content = msg.pop();
            assert (content != null);
            msg.destroy();

            //  Send 0..4 replies back
            int replies = rand.nextInt(5);
            for (int reply = 0; reply < replies; reply++) {
                //  Sleep for some fraction of a second
                try {
                    Thread.sleep(rand.nextInt(1000) + 1);
                }
                catch (InterruptedException e) {
                }
                address.send(worker, ZFrame.REUSE + ZFrame.MORE);
                content.send(worker, ZFrame.REUSE);
            }
            address.destroy();
            content.destroy();
        }
        context.destroy();
    }*/
}
