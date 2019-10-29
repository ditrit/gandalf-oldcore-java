package com.ditrit.gandalf.core.zeromqcore.library.listener;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.LinkedList;

public class ListenerCommandZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket frontEndListener;
    protected String frontEndListenerConnection;
    protected String identity;
    protected LinkedList<ZMsg> commands;

    protected void init(String identity, String frontEndListenerConnection) {
        this.context = new ZContext();
        this.identity = identity;
        commands = new LinkedList<>();

        this.frontEndListener = this.context.createSocket(SocketType.DEALER);
        this.frontEndListener.setIdentity(this.identity.getBytes(ZMQ.CHARSET));
        this.frontEndListenerConnection = frontEndListenerConnection;
        System.out.println("ThreadListenerCommandZeroMQ connect to frontEndListenerConnections: " +  this.frontEndListenerConnection);
        this.frontEndListener.connect( this.frontEndListenerConnection);
    }

    public ZMsg getCommandSync() {
        ZMsg command;
        boolean more = false;

        command = ZMsg.recvMsg(this.frontEndListener);
        more = this.frontEndListener.hasReceiveMore();
        System.out.println(command);
        System.out.println(more);

        return command;
    }

    public ZMsg getCommandAsync() {
        if(this.commands.isEmpty()) {
            return null;
        }
        return this.commands.poll();
    }

    private ZMsg updateHeaderBrokerMessage(ZMsg request) {
        request.removeFirst();
        return request;
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
