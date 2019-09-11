package com.orness.gandalf.core.module.zeromqcore.command.listener;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.LinkedList;
import java.util.List;

public class ThreadListenerCommandZeroMQ extends Thread {

    protected ZContext context;
    protected ZMQ.Socket frontEndListener;
    protected List<String> frontEndListenerConnections;
    protected String listenerConnector;
    private LinkedList<ZMsg> requests;

    public ThreadListenerCommandZeroMQ(String listenerConnector, List<String> frontEndListenerConnections) {
        this.init(listenerConnector, frontEndListenerConnections);
    }

    protected void init(String listenerConnector, List<String> frontEndListenerConnections) {
        this.context = new ZContext();
        this.listenerConnector = listenerConnector;
        this.frontEndListener = this.context.createSocket(SocketType.DEALER);
        this.frontEndListener.setIdentity(this.listenerConnector.getBytes(ZMQ.CHARSET));
        this.frontEndListenerConnections = frontEndListenerConnections;
        for(String connection : this.frontEndListenerConnections) {
            System.out.println("ThreadListenerCommandZeroMQ connect to frontEndListenerConnections: " + connection);
            this.frontEndListener.connect(connection);
        }
    }

    public ZMsg getCommandSync() {

        ZMsg command;
        boolean more = false;

        //while (true) {
        // Receive broker message
        command = ZMsg.recvMsg(this.frontEndListener);
        more = this.frontEndListener.hasReceiveMore();
        System.out.println(command);
        System.out.println(more);

/*            if (command == null) {
                break; // Interrupted
            }

            if(!more) {
                break;
            }
        }*/
        return command;
    }

    public ZMsg getCommandAsync() {
        if(this.isInterrupted()) {
            this.start();
        }
        if(this.requests.isEmpty()) {
            return null;
        }
        return this.requests.getLast();
    }

    @Override
    public void run() {
        ZMQ.Poller poller = this.context.createPoller(1);
        poller.register(this.frontEndListener, ZMQ.Poller.POLLIN);

        ZMsg request = null;
        boolean more = false;

        while (!Thread.currentThread().isInterrupted()) {
            poller.poll(1000);
            //Client
            if (poller.pollin(0)) {
                while (true) {
                    // Receive broker message
                    request = ZMsg.recvMsg(this.frontEndListener, ZMQ.NOBLOCK);
                    more = this.frontEndListener.hasReceiveMore();

                    System.out.println(request);
                    System.out.println(more);

                    if (request == null) {
                        break; // Interrupted
                    }
                    this.requests.add(request.duplicate());
                    System.out.println("SIZE");
                    System.out.println(requests.size());

                    if(!more) {
                        break;
                    }
                }
            }
        }
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("W: interrupted");
            poller.close();
            this.close(); // interrupted
        }
    }

    public void close() {
        if(this.isAlive()) {
            this.stop();
        }
        this.frontEndListener.close();
        this.context.close();
    }

    protected void reconnectToBroker() {
        if (this.frontEndListenerConnections != null) {
            this.context.destroySocket(frontEndListener);
        }
        this.init(this.listenerConnector, this.frontEndListenerConnections);
    }
}
