package com.ditrit.gandalf.core.zeromqcore.event.aggregator;

import com.google.gson.Gson;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.List;

public abstract class RunnableAggregatorSubscriberZeroMQ extends AggregatorSubscriberZeroMQ implements Runnable {

    protected Gson mapper;

    public RunnableAggregatorSubscriberZeroMQ() {
        super();
        mapper = new Gson();
    }

    protected void initRunnable(String routingSubscriberConnector, String frontEndSendRoutingSubscriberConnection, String backEndSendRoutingSubscriberConnection, List<String> frontEndReceiveRoutingSubscriberConnection, String backEndReceiveRoutingSubscriberConnection) {
        this.init(routingSubscriberConnector, frontEndSendRoutingSubscriberConnection, backEndSendRoutingSubscriberConnection, frontEndReceiveRoutingSubscriberConnection, backEndReceiveRoutingSubscriberConnection);
        //this.frontEndReceiveRoutingSubscriber.subscribe(ZMQ.SUBSCRIPTION_ALL);
        //this.backEndSendRoutingSubscriber.subscribe(ZMQ.SUBSCRIPTION_ALL);
        //this.frontEndRoutingSubscriber.subscribe("test.Test".getBytes(ZMQ.CHARSET));
    }

    @Override
    public void run() {
        //ZMQ.proxy(this.backEndSendRoutingSubscriber, this.frontEndSendRoutingSubscriber,  null);
        //ZMQ.proxy(this.frontEndReceiveRoutingSubscriber, this.backEndReceiveRoutingSubscriber,  null);
        // Initialize poll set
        ZMQ.Poller poller = context.createPoller(4);
        poller.register(this.frontEndSendRoutingSubscriber, ZMQ.Poller.POLLIN);
        poller.register(this.backEndSendRoutingSubscriber, ZMQ.Poller.POLLIN);
        poller.register(this.frontEndReceiveRoutingSubscriber, ZMQ.Poller.POLLIN);
        poller.register(this.backEndReceiveRoutingSubscriber, ZMQ.Poller.POLLIN);

        ZMsg publish;
        // Switch messages between sockets
        while (!Thread.currentThread().isInterrupted()) {
            poller.poll();
            //Client
            if (poller.pollin(0)) {
                while (true) {
                    // Receive broker message
                    publish = ZMsg.recvMsg(this.frontEndSendRoutingSubscriber);
                    System.out.println("BLOOP CLUSTER");
                    System.out.println(publish);
                    if (publish == null) {
                        break; // Interrupted
                    }
                    publish.send(this.backEndSendRoutingSubscriber);
                }
            }

            if (poller.pollin(1)) {
                while (true) {
                    // Receive broker message
                    publish = ZMsg.recvMsg(this.backEndSendRoutingSubscriber);
                    System.out.println("PUBLISH WORKER");
                    System.out.println(publish);
                    if (publish == null) {
                        break; // Interrupted
                    }
                    this.processWorkerPublish(publish);
                }
            }

            if (poller.pollin(2)) {
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

            if (poller.pollin(3)) {
                while (true) {
                    // Receive broker message
                    publish = ZMsg.recvMsg(this.backEndReceiveRoutingSubscriber);
                    System.out.println("BLOOP WORKER");
                    System.out.println(publish);
                    if (publish == null) {
                        break; // Interrupted
                    }
                    publish.send(this.frontEndReceiveRoutingSubscriber);
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
        this.sendToWorker(publish);
/*        if(publish.size() == 5) {
            this.sendToWorker(publish);
        }
        else {
            System.out.println("E: invalid message");
        }*/
        publish.destroy();
    }

    private void processWorkerPublish(ZMsg publish) {
        this.sendToProxy(publish);
/*
        if(publish.size() == 5) {
            this.sendToProxy(publish);
        }
        else {
            System.out.println("E: invalid message");
        }*/
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
