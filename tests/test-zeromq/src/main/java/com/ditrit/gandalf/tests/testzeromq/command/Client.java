package com.ditrit.gandalf.tests.testzeromq.command;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class Client {

    protected ZContext context;
    protected ZMQ.Socket backEndClient;
    private String[] backEndClientConnections;
    protected String identity;

    public Client() {

    }

    protected void init(String identity, String[] backEndClientConnections) {
        this.context = new ZContext();
        this.identity = identity;

        //Broker
        this.backEndClient = this.context.createSocket(SocketType.DEALER);
        this.backEndClient.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.backEndClientConnections = backEndClientConnections;
        for(String connection : this.backEndClientConnections) {
            System.out.println("ClientZeroMQ connect to: " + connection);
            this.backEndClient.connect(connection);
        }
    }

    public void close() {
        this.backEndClient.close();
        this.context.close();
    }

    public void sendCommand(String uuid, String connector, String serviceClass, String command, String payload) {
        this.backEndClient.sendMore(uuid);
        this.backEndClient.sendMore(connector);
        this.backEndClient.sendMore(serviceClass);
        this.backEndClient.sendMore(command);
        this.backEndClient.send(payload);
    }

}
