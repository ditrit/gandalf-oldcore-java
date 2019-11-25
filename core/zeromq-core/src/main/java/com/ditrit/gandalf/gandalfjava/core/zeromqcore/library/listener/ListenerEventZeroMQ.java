package com.ditrit.gandalf.gandalfjava.core.zeromqcore.library.listener;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.LinkedList;

public class ListenerEventZeroMQ extends Thread {

    protected ZContext context;
    protected ZMQ.Socket frontEndListener;
    protected String frontEndListenerConnection;
    protected String identity;
    protected LinkedList<ZMsg> events;

    protected void init(String identity, String frontEndListenerConnection) {
        this.context = new ZContext();
        this.identity = identity;
        events = new LinkedList<>();

        this.frontEndListener = this.context.createSocket(SocketType.SUB);
        this.frontEndListenerConnection = frontEndListenerConnection;
        System.out.println("ThreadListenerEventZeroMQ connect to frontEndListenerConnection: " + this.frontEndListenerConnection);
        this.frontEndListener.subscribe(ZMQ.SUBSCRIPTION_ALL);
    }

    public ZMsg getEventSync() {
        ZMsg event;
        boolean more = false;

        event = ZMsg.recvMsg(this.frontEndListener);
        more = this.frontEndListener.hasReceiveMore();
        System.out.println(event);
        System.out.println(more);

        return event;
    }

    public ZMsg getEventAsync() {
        if(this.events.isEmpty()) {
            return null;
        }
        return this.events.poll();
    }

    public void close() {
        this.frontEndListener.close();
        this.context.close();
    }

    protected void reconnect() {
        if (this.frontEndListenerConnection != null) {
            this.context.destroySocket(frontEndListener);
        }
        this.init(this.identity, this.frontEndListenerConnection);
    }
}
