package com.ditrit.gandalf.core.zeromqcore.command.listener;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.List;

public class ListenerCommandZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket frontEndListener;
    protected List<String> frontEndListenerConnections;
    protected String listenerConnector;

    public ListenerCommandZeroMQ(String listenerConnector, List<String> frontEndListenerConnections) {
        this.init(listenerConnector, frontEndListenerConnections);
    }

    protected void init(String listenerConnector, List<String> frontEndListenerConnections) {
        this.context = new ZContext();
        this.listenerConnector = listenerConnector;
        this.frontEndListener = this.context.createSocket(SocketType.DEALER);
        this.frontEndListener.setIdentity(this.listenerConnector.getBytes(ZMQ.CHARSET));
        this.frontEndListenerConnections = frontEndListenerConnections;
        for(String connection : this.frontEndListenerConnections) {
            System.out.println("ListenerCommandZeroMQ connect to frontEndListenerConnections: " + connection);
            this.frontEndListener.connect(connection);
        }
    }

    public ZMsg getCommand() {

        ZMsg command;
        boolean more = false;

        //while (true) {
            // Receive broker message
            command = ZMsg.recvMsg(this.frontEndListener);
            more = this.frontEndListener.hasReceiveMore();
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
        this.frontEndListener.close();
        this.context.close();
    }

    protected void reconnectToBroker() {
        if (this.frontEndListenerConnections != null) {
            this.context.destroySocket(frontEndListener);
        }
        this.init(this.listenerConnector, this.frontEndListenerConnections);
    }
}
