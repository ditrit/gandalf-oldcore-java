package com.ditrit.gandalf.core.zeromqcore.service.listener;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class ListenerServiceZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket serviceListener;
    protected String serviceListenerConnection;
    protected String identity;

    protected void init(String identity, String serviceListenerConnection) {
        this.context = new ZContext();
        this.identity = identity;

        this.serviceListener = this.context.createSocket(SocketType.REP);
        this.serviceListener.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.serviceListenerConnection = serviceListenerConnection;
        System.out.println("ListenerCommandZeroMQ bind to frontEndListenerConnections: " + this.serviceListenerConnection);
        this.serviceListener.bind(this.serviceListenerConnection);
    }

    public void close() {
        this.serviceListener.close();
        this.context.close();
    }
}
