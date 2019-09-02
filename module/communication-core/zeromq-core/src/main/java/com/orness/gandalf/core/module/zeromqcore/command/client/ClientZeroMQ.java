package com.orness.gandalf.core.module.zeromqcore.command.client;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.List;

public class ClientZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket backEndClient;
    private List<String> backEndClientConnections;
    protected String identity;

    public ClientZeroMQ() {

    }

    protected void init(String identity, List<String> backEndClientConnections) {
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