package com.orness.gandalf.core.test.testzeromq.event;

import com.google.gson.Gson;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

import static com.orness.gandalf.core.test.testzeromq.Constant.*;

public abstract class RunnableRoutingSubscriber extends RoutingSubscriber implements Runnable {

    protected Gson mapper;
    private List<String> topics;
    private Deque<ZMsg> deque;

    public RunnableRoutingSubscriber() {
        super();
        mapper = new Gson();
        this.deque = new ArrayDeque<>();
    }

    public void initRunnable(String routingSubscriberConnector, String frontEndRoutingSubcriberConnection, String backEndRoutingSubscriberConnection, List<String> topics) {
        this.init(routingSubscriberConnector, frontEndRoutingSubcriberConnection, backEndRoutingSubscriberConnection);
        this.topics = topics;
        for(String topic : this.topics) {
            this.frontEndRoutingSubscriber.subscribe(topic.getBytes());
        }
    }

    @Override
    public void run() {
        // Initialize poll set
        ZMQ.Poller poller = context.createPoller(2);
        poller.register(this.frontEndRoutingSubscriber, ZMQ.Poller.POLLIN);
        poller.register(this.backEndRoutingSubscriber, ZMQ.Poller.POLLIN);

        ZMsg publish;
        ZMsg response;

        // Switch messages between sockets
        while (!Thread.currentThread().isInterrupted()) {
            //Client
            if (poller.pollin(0)) {
                while (true) {
                    // Receive broker message
                    publish = ZMsg.recvMsg(this.frontEndRoutingSubscriber);
                    System.out.println("POLL 0");
                    System.out.println(publish);
                    if (publish == null) {
                        break; // Interrupted
                    }
                    // Broker it
                    this.processProxyPublish(publish);
                }
            }
            //Worker
            if (poller.pollin(1)) {
                while (true) {
                    // Receive command message
                    response = ZMsg.recvMsg(this.backEndRoutingSubscriber);
                    System.out.println("POLL 1");
                    System.out.println(response);
                    if (response == null) {
                        break; // Interrupted
                    }
                    // Broker it
                    this.processWorkerResponse(response);
                }
            }
            poller.close();
        }
        if (Thread.currentThread().isInterrupted()) {
            System.out.println("W: interrupted");
            this.close(); // interrupted
        }
    }

    private void processProxyPublish(ZMsg publish) {
        ZMsg publishBackup = publish.duplicate();
        this.deque.addLast(publish);
    }

    private void processWorkerResponse(ZMsg response) {
        ZMsg responseBackup = response.duplicate();
        String commandType = responseBackup.popString();
        String event;
        if (commandType.equals(COMMAND_COMMAND_READY)) {
            event = responseBackup.popString();
            if(this.deque.getFirst() != null) {
                this.sendToWorker(this.deque.getFirst());
            }
        }
        else {
            System.out.println("E: invalid message");
        }
        //msg.destroy();
    }

    public void sendToWorker(ZMsg publish) {
        //Command
        publish.send(this.backEndRoutingSubscriber);
    }

    public void addTopic(String topic) {
        this.topics.add(topic);
        this.frontEndRoutingSubscriber.subscribe(topic.getBytes());
    }
}
