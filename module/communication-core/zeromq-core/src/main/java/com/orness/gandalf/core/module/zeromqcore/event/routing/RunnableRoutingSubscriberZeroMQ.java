package com.orness.gandalf.core.module.zeromqcore.event.routing;

import com.google.gson.Gson;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.zeromqcore.constant.Constant.EVENT_CLIENT_SEND;

public abstract class RunnableRoutingSubscriberZeroMQ extends RoutingSubscriberZeroMQ implements Runnable {

    protected Gson mapper;

    public RunnableRoutingSubscriberZeroMQ() {
        super();
        mapper = new Gson();
    }

    protected void initRunnable(String routingSubscriberConnector, String frontEndRoutingSubcriberConnection, String backEndRoutingSubscriberConnection) {
        this.init(routingSubscriberConnector, frontEndRoutingSubcriberConnection, backEndRoutingSubscriberConnection);
        this.frontEndRoutingSubscriber.subscribe(ZMQ.SUBSCRIPTION_ALL);
        //this.frontEndRoutingSubscriber.subscribe("test.Test".getBytes(ZMQ.CHARSET));
    }

    @Override
    public void run() {
        // Initialize poll set
        ZMQ.Poller poller = context.createPoller(1);
        poller.register(this.frontEndRoutingSubscriber, ZMQ.Poller.POLLIN);

        ZMsg publish;

        // Switch messages between sockets
        while (!Thread.currentThread().isInterrupted()) {
            poller.poll();
            //Client
            if (poller.pollin(0)) {
                while (true) {
                    // Receive broker message
                    publish = ZMsg.recvMsg(this.frontEndRoutingSubscriber);
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
        ZMsg publishBackup = publish.duplicate();
        String commandType = publishBackup.popString();
        if(commandType.equals(EVENT_CLIENT_SEND)) {
            this.sendToWorker(publish);
        }
        else {
            System.out.println("E: invalid message");
        }
        publishBackup.destroy();
        publish.destroy();
    }

    public void sendToWorker(ZMsg publish) {
        //Command
        publish.send(this.backEndRoutingSubscriber);
    }
}
