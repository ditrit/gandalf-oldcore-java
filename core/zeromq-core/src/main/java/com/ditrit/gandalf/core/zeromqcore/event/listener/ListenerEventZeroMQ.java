package com.ditrit.gandalf.core.zeromqcore.event.listener;

import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public class ListenerEventZeroMQ {

    protected ZContext context;
    protected ZMQ.Socket frontEndListener;
    protected String frontEndListenerConnection;
    protected String listenerConnector;

    public ListenerEventZeroMQ(String listenerConnector, String frontEndListenerConnection) {
        this.init(listenerConnector, frontEndListenerConnection);
    }

    protected void init(String listenerConnector, String frontEndListenerConnection) {
        this.context = new ZContext();
        this.listenerConnector = listenerConnector;
        this.frontEndListener = this.context.createSocket(SocketType.SUB);
        this.frontEndListenerConnection = frontEndListenerConnection;
        System.out.println("ListenerEventZeroMQ connect to frontEndListenerConnection: " + this.frontEndListenerConnection);
        this.frontEndListener.subscribe(ZMQ.SUBSCRIPTION_ALL);
    }

    public ZMsg getEvent() {

        ZMsg event;
        boolean more = false;

        //while (true) {
            // Receive broker message
            event = ZMsg.recvMsg(this.frontEndListener);
            more = this.frontEndListener.hasReceiveMore();
            System.out.println(event);
            System.out.println(more);

/*            if (event == null) {
                break; // Interrupted
            }

            if(!more) {
                break;
            }
        }*/
        return event;
    }

    public void close() {
        this.frontEndListener.close();
        this.context.close();
    }

    protected void reconnectToProxy() {
        if (this.frontEndListenerConnection != null) {
            this.context.destroySocket(frontEndListener);
        }
        this.init(this.listenerConnector, this.frontEndListenerConnection);
    }
}
