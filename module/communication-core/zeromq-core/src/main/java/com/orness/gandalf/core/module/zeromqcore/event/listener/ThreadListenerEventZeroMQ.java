package com.orness.gandalf.core.module.zeromqcore.event.listener;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.LinkedList;

public class ThreadListenerEventZeroMQ extends Thread {

    protected ZContext context;
    protected ZMQ.Socket frontEndListener;
    protected String frontEndListenerConnection;
    protected String listenerConnector;
    private LinkedList<ZMsg> events;

    public ThreadListenerEventZeroMQ(String listenerConnector, String frontEndListenerConnection) {
        this.init(listenerConnector, frontEndListenerConnection);
    }

    protected void init(String listenerConnector, String frontEndListenerConnection) {
        this.context = new ZContext();
        this.listenerConnector = listenerConnector;
        events = new LinkedList<>();

        this.frontEndListener = this.context.createSocket(SocketType.SUB);
        this.frontEndListenerConnection = frontEndListenerConnection;
        System.out.println("ThreadListenerEventZeroMQ connect to frontEndListenerConnection: " + this.frontEndListenerConnection);
        this.frontEndListener.subscribe(ZMQ.SUBSCRIPTION_ALL);
    }

    public ZMsg getEventSync() {

        ZMsg event;
        boolean more = false;

        //while (true) {
        // Receive broker message
        event = ZMsg.recvMsg(this.frontEndListener);
        more = this.frontEndListener.hasReceiveMore();
        System.out.println(event);
        System.out.println(more);

/*            if (event == null) {
                break; // Interrupted
            }

            if(!more) {
                break;
            }
        }*/
        return event;
    }

    public ZMsg getEventAsync() {
        if(this.events.isEmpty()) {
            return null;
        }
        return this.events.poll();
    }

    @Override
    public void run() {
        ZMQ.Poller poller = this.context.createPoller(1);
        poller.register(this.frontEndListener, ZMQ.Poller.POLLIN);

        ZMsg event = null;
        boolean more = false;

        while (!Thread.currentThread().isInterrupted()) {
            poller.poll(1000);
            //Client
            if (poller.pollin(0)) {
                while (true) {
                    // Receive broker message
                    event = ZMsg.recvMsg(this.frontEndListener, ZMQ.NOBLOCK);
                    more = this.frontEndListener.hasReceiveMore();

                    System.out.println(event);
                    System.out.println(more);

                    if (event == null) {
                        break; // Interrupted
                    }
                    this.events.add(event.duplicate());

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
        this.frontEndListener.close();
        this.context.close();
    }

    protected void reconnectToProxy() {
        if (this.frontEndListenerConnection != null) {
            this.context.destroySocket(frontEndListener);
        }
        this.init(this.listenerConnector, this.frontEndListenerConnection);
    }
}
