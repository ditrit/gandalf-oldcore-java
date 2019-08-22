package com.orness.gandalf.core.test.testzeromq.command;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Client {

    private ZContext context;
    protected ZMQ.Socket backEndClient;
    private String backEndClientConnection;
    protected String identity;

    public Client() {
        this.context = new ZContext();
        this.backEndClient = this.context.createSocket(SocketType.DEALER);
    }

    public void init(String backEndClientConnection, String identity) {
        this.backEndClientConnection = backEndClientConnection;
        this.identity = identity;
        System.out.println("ClientZeroMQ connect to: " + this.backEndClientConnection);
        this.backEndClient.bind(this.backEndClientConnection);
    }

    public void close() {
        this.backEndClient.close();
        this.context.close();
    }
}
