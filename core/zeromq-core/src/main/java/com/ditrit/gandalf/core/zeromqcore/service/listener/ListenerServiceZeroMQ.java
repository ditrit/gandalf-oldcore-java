package com.ditrit.gandalf.core.zeromqcore.service.listener;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public abstract class ListenerServiceZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket frontEndListener;
    protected String frontEndListenerConnection;
    protected String identity;

    public ListenerServiceZeroMQ() {
    }

    protected void init(String identity, String frontEndListenerConnection) {
        this.context = new ZContext();
        this.identity = identity;

        this.frontEndListener = this.context.createSocket(SocketType.REQ);
        this.frontEndListener.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.frontEndListenerConnection = frontEndListenerConnection;
        System.out.println("ThreadListenerCommandZeroMQ connect to frontEndListenerConnections: " + this.frontEndListenerConnection);
        this.frontEndListener.connect(this.frontEndListenerConnection);

    }

    protected void reconnectToBroker() {
        if (this.frontEndListenerConnection != null) {
            this.context.destroySocket(frontEndListener);
        }
        this.init(this.identity, this.frontEndListenerConnection);
    }

    protected void close() {
        this.frontEndListener.close();
        this.context.close();
    }
}
