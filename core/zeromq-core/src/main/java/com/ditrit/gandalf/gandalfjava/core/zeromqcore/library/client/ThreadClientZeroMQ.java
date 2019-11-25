package com.ditrit.gandalf.gandalfjava.core.zeromqcore.library.client;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

public class ThreadClientZeroMQ extends Thread {

    protected ZContext context;
    protected ZMQ.Socket backEndClient;
    private List<String> backEndClientConnections;
    private String backEndClientConnection;
    protected String identity;
    private LinkedList<ZMsg> responses;

    protected void init(String identity, String backEndClientConnection) {
        this.context = new ZContext();
        this.identity = identity;
        responses = new LinkedList<>();

        //Broker
        this.backEndClient = this.context.createSocket(SocketType.DEALER);
        this.backEndClient.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.backEndClientConnection = backEndClientConnection;
        System.out.println("ClientZeroMQ connect to: " + backEndClientConnection);
        this.backEndClient.connect(this.backEndClientConnection);
    }

    public ThreadClientZeroMQ(String identity, List<String> backEndClientConnections) {
        this.initList(identity, backEndClientConnections);
    }

    protected void initList(String identity, List<String> backEndClientConnections) {
        this.context = new ZContext();
        this.identity = identity;
        responses = new LinkedList<>();

        this.backEndClient = this.context.createSocket(SocketType.DEALER);
        this.backEndClient.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.backEndClientConnections = backEndClientConnections;
        for(String connection : this.backEndClientConnections) {
            System.out.println("ClientZeroMQ connect to: " + connection);
            this.backEndClient.connect(connection);
        }
    }

    public ZMsg sendCommandSync(String context, String timeout, String uuid, String command, String payload) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        this.backEndClient.sendMore(""); // SOURCE AGGREGATOR
        this.backEndClient.sendMore(""); // SOURCE CONNECTOR
        this.backEndClient.sendMore(""); // SOURCE WORKER
        this.backEndClient.sendMore(""); // TARGET AGGREGATOR
        this.backEndClient.sendMore(""); // TARGET CONNECTOR
        this.backEndClient.sendMore(""); // TARGET WORKER
        this.backEndClient.sendMore(""); // TENANT
        this.backEndClient.sendMore(""); //TOKEN
        this.backEndClient.sendMore(context); //CONTEXT
        this.backEndClient.sendMore(timeout); //TIMEOUT
        this.backEndClient.sendMore(timestamp.toString()); //TIMESTAMP
        this.backEndClient.sendMore(""); //MAJOR
        this.backEndClient.sendMore(""); //MINOR
        this.backEndClient.sendMore(uuid); //UUID
        this.backEndClient.sendMore(command); //COMMAND
        this.backEndClient.send(payload); //PAYLOAD
        return this.getCommandResultSync();
    }

    public void sendCommandAsync(String context, String timeout, String uuid, String command, String payload) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        this.backEndClient.sendMore(""); // SOURCE AGGREGATOR
        this.backEndClient.sendMore(""); // SOURCE CONNECTOR
        this.backEndClient.sendMore(""); // SOURCE WORKER
        this.backEndClient.sendMore(""); // TARGET AGGREGATOR
        this.backEndClient.sendMore(""); // TARGET CONNECTOR
        this.backEndClient.sendMore(""); // TARGET WORKER
        this.backEndClient.sendMore(""); // TENANT
        this.backEndClient.sendMore(""); //TOKEN
        this.backEndClient.sendMore(context); //CONTEXT
        this.backEndClient.sendMore(timeout); //TIMEOUT
        this.backEndClient.sendMore(timestamp.toString()); //TIMESTAMP
        this.backEndClient.sendMore(""); //MAJOR
        this.backEndClient.sendMore(""); //MINOR
        this.backEndClient.sendMore(uuid); //UUID
        this.backEndClient.sendMore(command); //COMMAND
        this.backEndClient.send(payload); //PAYLOAD
        this.run();
    }

    private ZMsg getCommandResultSync() {

        ZMsg response;
        boolean more = false;

        response = ZMsg.recvMsg(this.backEndClient);
        more = this.backEndClient.hasReceiveMore();
        System.out.println(response);
        System.out.println(more);

        return response;
    }

    public ZMsg getCommandResultAsync() {
        if(this.responses.isEmpty()) {
            return null;
        }
        return this.responses.poll();
    }

    @Override
    public void run() {
        ZMQ.Poller poller = this.context.createPoller(1);
        poller.register(this.backEndClient, ZMQ.Poller.POLLIN);

        ZMsg response = null;
        boolean more = false;

        while (!Thread.currentThread().isInterrupted()) {
            poller.poll(1000);

            if (poller.pollin(0)) {
                while (true) {
                    response = ZMsg.recvMsg(this.backEndClient, ZMQ.NOBLOCK);
                    more = this.backEndClient.hasReceiveMore();

                    System.out.println(response);
                    System.out.println(more);

                    if (response == null) {
                        break;
                    }
                    this.responses.add(response.duplicate());

                    if(!more) {
                        break;
                    }
                }
            }
        }
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("W: interrupted");
            poller.close();
            this.close();
        }
    }

    public void close() {
        this.backEndClient.close();
        this.context.close();
    }
}
