package com.orness.gandalf.core.test.testzeromq.event;

import com.google.gson.Gson;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.List;

public abstract class RunnableRoutingSubscriber extends RoutingSubscriber implements Runnable {

    protected Gson mapper;
    private List<String> topics;

    public RunnableRoutingSubscriber() {
        super();
        mapper = new Gson();
    }

    protected void initRunnable(String routingSubscriberConnector, String frontEndRoutingSubcriberConnection, String backEndRoutingSubscriberConnection, List<String> topics) {
        this.init(routingSubscriberConnector, frontEndRoutingSubcriberConnection, backEndRoutingSubscriberConnection);
        this.topics = topics;
        for(String topic : this.topics) {
            this.frontEndRoutingSubscriber.subscribe(topic.getBytes());
        }
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
        this.sendToWorker(publish);
        publish.destroy();
    }

    public void sendToWorker(ZMsg publish) {
        //Command
        publish.addFirst(this.ROUTING_TYPE);
        publish.send(this.backEndRoutingSubscriber);
    }

    public void addTopic(String topic) {
        this.topics.add(topic);
        this.frontEndRoutingSubscriber.subscribe(topic.getBytes());
    }
}
