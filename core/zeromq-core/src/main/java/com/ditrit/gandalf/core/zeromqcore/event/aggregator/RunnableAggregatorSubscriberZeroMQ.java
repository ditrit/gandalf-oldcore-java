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
    }

    @Override
    public void run() {
        // Initialize poll set
        ZMQ.Poller poller = context.createPoller(4);
        poller.register(this.frontEndSendRoutingSubscriber, ZMQ.Poller.POLLIN);
        poller.register(this.backEndSendRoutingSubscriber, ZMQ.Poller.POLLIN);
        poller.register(this.frontEndReceiveRoutingSubscriber, ZMQ.Poller.POLLIN);
        poller.register(this.backEndReceiveRoutingSubscriber, ZMQ.Poller.POLLIN);

        ZMsg publish;
        boolean more = false;

        // Switch messages between sockets
        while (!Thread.currentThread().isInterrupted()) {
            poller.poll();
            //Client
            if (poller.pollin(0)) {
                while (true) {
                    publish = ZMsg.recvMsg(this.frontEndSendRoutingSubscriber);
                    more = this.frontEndSendRoutingSubscriber.hasReceiveMore();
                    System.out.println("BLOOP CLUSTER");
                    System.out.println(publish);
                    if (publish == null) {
                        break; // Interrupted
                    }
                    publish.send(this.backEndSendRoutingSubscriber);

                    if(!more) {
                        break;
                    }
                }
            }

            if (poller.pollin(1)) {
                while (true) {
                    publish = ZMsg.recvMsg(this.backEndSendRoutingSubscriber);
                    more = this.backEndSendRoutingSubscriber.hasReceiveMore();
                    System.out.println("PUBLISH WORKER");
                    System.out.println(publish);
                    if (publish == null) {
                        break; // Interrupted
                    }
                    this.processWorkerPublish(publish);

                    if(!more) {
                        break;
                    }
                }
            }

            if (poller.pollin(2)) {
                while (true) {
                    publish = ZMsg.recvMsg(this.frontEndReceiveRoutingSubscriber);
                    more = this.frontEndReceiveRoutingSubscriber.hasReceiveMore();
                    System.out.println("PUBLISH CLUSTER");
                    System.out.println(publish);
                    if (publish == null) {
                        break; // Interrupted
                    }
                    this.processProxyPublish(publish);

                    if(!more) {
                        break;
                    }
                }
            }

            if (poller.pollin(3)) {
                while (true) {
                    publish = ZMsg.recvMsg(this.backEndReceiveRoutingSubscriber);
                    more = this.backEndReceiveRoutingSubscriber.hasReceiveMore();
                    System.out.println("BLOOP WORKER");
                    System.out.println(publish);
                    if (publish == null) {
                        break; // Interrupted
                    }
                    publish.send(this.frontEndReceiveRoutingSubscriber);

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

    private void processProxyPublish(ZMsg publish) {
        this.sendToWorker(publish);
        publish.destroy();
    }

    private void processWorkerPublish(ZMsg publish) {
        this.sendToProxy(publish);
        publish.destroy();
    }

    public void sendToWorker(ZMsg publish) {
        publish.send(this.backEndReceiveRoutingSubscriber);
    }

    public void sendToProxy(ZMsg publish) {
        publish.send(this.frontEndSendRoutingSubscriber);
    }
}
