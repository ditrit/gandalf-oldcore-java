package com.orness.gandalf.core.test.testzeromq.command;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Client {

    private ZContext context;
    protected ZMQ.Socket backEndClient;
    private String[] backEndClientConnections;
    protected String identity;

    public Client() {

    }

    public void init(String identity, String[] backEndClientConnections) {
        this.context = new ZContext();
        this.identity = identity;
        this.backEndClientConnections = backEndClientConnections;

        this.backEndClient = this.context.createSocket(SocketType.DEALER);
        this.backEndClient.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        for(String connection : this.backEndClientConnections) {
            System.out.println("ClientZeroMQ connect to: " + connection);
            this.backEndClient.connect(connection);
        }
    }

    public void close() {
        this.backEndClient.close();
        this.context.close();
    }

}
