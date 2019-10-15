package com.ditrit.gandalf.core.zeromqcore.service.listener;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.List;

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
        System.out.println("ListenerCommandZeroMQ connect to frontEndListenerConnections: " + this.serviceListenerConnection);
        this.serviceListener.connect(this.serviceListenerConnection);

    }

    public ZMsg getCommand() {

        ZMsg command;
        boolean more = false;

        //while (true) {
        // Receive broker message
        command = ZMsg.recvMsg(this.serviceListener);
        more = this.serviceListener.hasReceiveMore();
        System.out.println(command);
        System.out.println(more);

/*            if (command == null) {
                break; // Interrupted
            }

            if(!more) {
                break;
            }
        }*/
        return command;
    }


    public void close() {
        this.serviceListener.close();
        this.context.close();
    }

    protected void reconnectToBroker() {
        if (this.serviceListenerConnection != null) {
            this.context.destroySocket(serviceListener);
        }
        this.init(this.identity, this.serviceListenerConnection);
    }
}
