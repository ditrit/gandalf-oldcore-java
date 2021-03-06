package com.ditrit.gandalf.core.zeromqcore.command.client;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
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

    public ThreadClientZeroMQ(String identity, String backEndClientConnection) {
        this.init(identity, backEndClientConnection);
    }

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

        //Broker
        this.backEndClient = this.context.createSocket(SocketType.DEALER);
        this.backEndClient.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.backEndClientConnections = backEndClientConnections;
        for(String connection : this.backEndClientConnections) {
            System.out.println("ClientZeroMQ connect to: " + connection);
            this.backEndClient.connect(connection);
        }
    }

    public ZMsg sendCommandSync(String uuid, String connector, String serviceClass, String command, String timeout, String payload) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        this.backEndClient.sendMore(uuid);
        this.backEndClient.sendMore(connector);
        this.backEndClient.sendMore(serviceClass);
        this.backEndClient.sendMore(command);
        this.backEndClient.sendMore(timeout);
        this.backEndClient.sendMore(timestamp.toString());
        this.backEndClient.send(payload);
        return this.getCommandResultSync();
    }

    public void sendCommandAsync(String uuid, String connector, String serviceClass, String command, String timeout, String payload) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        this.backEndClient.sendMore(uuid);
        this.backEndClient.sendMore(connector);
        this.backEndClient.sendMore(serviceClass);
        this.backEndClient.sendMore(command);
        this.backEndClient.sendMore(timeout);
        this.backEndClient.sendMore(timestamp.toString());
        this.backEndClient.send(payload);
        this.run();
    }


    //TODO REMOVE
    public ZMsg sendCustomCommandSync(String uuid, String connector, String serviceClass, String command, String timeout, String payload) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        this.backEndClient.sendMore(uuid);
        this.backEndClient.sendMore(this.identity);
        this.backEndClient.sendMore(this.identity);
        this.backEndClient.sendMore(connector);
        this.backEndClient.sendMore(serviceClass);
        this.backEndClient.sendMore(command);
        this.backEndClient.sendMore(timeout);
        this.backEndClient.sendMore(timestamp.toString());
        this.backEndClient.send(payload);
        return this.getCommandResultSync();
    }

    //TODO REMOVE
    public void sendCustomCommandAsync(String uuid, String connector, String serviceClass, String command, String timeout, String payload) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        this.backEndClient.sendMore(uuid);
        this.backEndClient.sendMore(this.identity);
        this.backEndClient.sendMore(this.identity);
        this.backEndClient.sendMore(connector);
        this.backEndClient.sendMore(serviceClass);
        this.backEndClient.sendMore(command);
        this.backEndClient.sendMore(timeout);
        this.backEndClient.sendMore(timestamp.toString());
        this.backEndClient.send(payload);
        this.run();
    }

    private ZMsg getCommandResultSync() {

        ZMsg response;
        boolean more = false;

        //while (true) {
        // Receive broker message
        response = ZMsg.recvMsg(this.backEndClient);
        more = this.backEndClient.hasReceiveMore();
        System.out.println(response);
        System.out.println(more);

/*            if (command == null) {
                break; // Interrupted
            }

            if(!more) {
                break;
            }
        }*/
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
            //Client
            if (poller.pollin(0)) {
                while (true) {
                    response = ZMsg.recvMsg(this.backEndClient, ZMQ.NOBLOCK);
                    more = this.backEndClient.hasReceiveMore();

                    System.out.println(response);
                    System.out.println(more);

                    if (response == null) {
                        break; // Interrupted
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
            this.close(); // interrupted
        }
    }

    public void close() {
        this.backEndClient.close();
        this.context.close();
    }
}
