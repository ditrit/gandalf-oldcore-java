package com.orness.gandalf.core.test.testzeromq.event;

import com.google.gson.Gson;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static com.orness.gandalf.core.test.testzeromq.Constant.*;

public abstract class RunnableRoutingSubscriber extends RoutingSubscriber implements Runnable {

    protected Gson mapper;
    private String topic;
    private Map<String, Deque<ZMsg>> serviceClassWorkerDeque;

    public RunnableRoutingSubscriber() {
        super();
        mapper = new Gson();
    }

    public void initRunnable(String routingSubscriberConnector, String frontEndRoutingSubcriberConnection, String backEndRoutingSubscriberConnection, String topic) {
        this.init(routingSubscriberConnector, frontEndRoutingSubcriberConnection, backEndRoutingSubscriberConnection);
        this.topic = topic;
        this.frontEndRoutingSubscriber.subscribe(this.topic.getBytes());
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

            // Client
            if (poller.pollin(0)) {
                while (true) {
                    // Receive broker message
                    publish = ZMsg.recvMsg(this.frontEndRoutingSubscriber);
                    if (publish == null) {
                        break; // Interrupted
                    }
                    // Broker it
                    this.processProxyPublish(publish);
                }
            }

            // Worker
            if (poller.pollin(1)) {
                while (true) {
                    // Receive command message
                    response = ZMsg.recvMsg(this.backEndRoutingSubscriber);
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
        String uuid = publishBackup.popString();
        String topic = publishBackup.popString();
        String typeEvent = publishBackup.popString();
        String event;
        if(typeEvent.contains(WORKER_COMMAND_EVENT)) {
            event = publishBackup.popString();
            this.serviceClassWorkerDeque.get(event).addLast(publish);
        }
    }

    private void processWorkerResponse(ZMsg response) {

        ZMsg responseBackup = response.duplicate();
        String commandType = responseBackup.popString();
        String event;
        if (commandType.equals(COMMAND_COMMAND_READY)) {
            event = responseBackup.popString();
            if(this.serviceClassWorkerDeque.containsKey(event)) {
                if(this.serviceClassWorkerDeque.get(event).getFirst() != null) {
                    this.sendToWorker(this.serviceClassWorkerDeque.get(event).getFirst());
                }
            }
            else {
                this.serviceClassWorkerDeque.put(event, new ArrayDeque<>());
            }
        }
        else if (commandType.equals(WORKER_COMMAND_RESULT)) {
            //TODO
            //this.sendToBroker(response);
        }
        else {
            System.out.println("E: invalid message");
        }
        //TODO
        //msg.destroy();
    }

    public void sendToWorker(ZMsg publish) {
        //Command
        publish.send(this.backEndRoutingSubscriber);
    }
}
