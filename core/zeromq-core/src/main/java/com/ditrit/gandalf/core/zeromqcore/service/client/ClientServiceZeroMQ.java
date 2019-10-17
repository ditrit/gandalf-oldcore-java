package com.ditrit.gandalf.core.zeromqcore.service.client;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public abstract class ClientServiceZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket serviceClient;
    private String serviceClientConnection;
    protected String identity;


    protected void init(String identity, String serviceClientConnection) {
        this.context = new ZContext();
        this.identity = identity;

        this.serviceClient = this.context.createSocket(SocketType.REQ);
        this.serviceClient.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.serviceClientConnection = serviceClientConnection;
        System.out.println("ClientServiceZeroMQ connect to: " + this.serviceClientConnection);
        this.serviceClient.connect(this.serviceClientConnection);
    }

    protected void close() {
        this.serviceClient.close();
        this.context.close();
    }
}
