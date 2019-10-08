package com.ditrit.gandalf.core.zeromqcore.event.routing;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.google.gson.Gson;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

public abstract class RunnableRoutingSubscriberZeroMQ extends RoutingSubscriberZeroMQ implements Runnable {

    protected Gson mapper;

    public RunnableRoutingSubscriberZeroMQ() {
        super();
        mapper = new Gson();
    }

    protected void initRunnable(String routingSubscriberConnector, String frontEndSendRoutingSubscriberConnection, String backEndSendRoutingSubscriberConnection, String frontEndReceiveRoutingSubscriberConnection, String backEndReceiveRoutingSubscriberConnection) {
        this.init(routingSubscriberConnector, frontEndSendRoutingSubscriberConnection, backEndSendRoutingSubscriberConnection, frontEndReceiveRoutingSubscriberConnection, backEndReceiveRoutingSubscriberConnection);
        this.frontEndReceiveRoutingSubscriber.subscribe(ZMQ.SUBSCRIPTION_ALL);
        //this.frontEndRoutingSubscriber.subscribe("test.Test".getBytes(ZMQ.CHARSET));
    }

    @Override
    public void run() {
        // Initialize poll set
        ZMQ.Poller poller = context.createPoller(1);
        poller.register(this.frontEndReceiveRoutingSubscriber, ZMQ.Poller.POLLIN);
        //poller.register(this.frontEndSendRoutingSubscriber, ZMQ.Poller.POLLIN);

        ZMsg publish;

        // Switch messages between sockets
        while (!Thread.currentThread().isInterrupted()) {
            poller.poll();
            //Client
            if (poller.pollin(0)) {
                while (true) {
                    // Receive broker message
                    publish = ZMsg.recvMsg(this.frontEndReceiveRoutingSubscriber);
                    System.out.println("PUBLISH CLUSTER");
                    System.out.println(publish);
                    if (publish == null) {
                        break; // Interrupted
                    }
                    this.processProxyPublish(publish);
                }
            }
        }
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("W: interrupted");
            poller.close();
            this.close(); // interrupted
        }
    }

    private void processProxyPublish(ZMsg publish) {
        if(publish.size() == 5) {
            this.sendToWorker(publish);
        }
        else {
            System.out.println("E: invalid message");
        }
        publish.destroy();
    }

    public void sendToWorker(ZMsg publish) {
        //Command
        publish.send(this.backEndReceiveRoutingSubscriber);
    }

    public void sendToProxy(ZMsg publish) {
        //Command
        publish.send(this.frontEndSendRoutingSubscriber);
    }
}
