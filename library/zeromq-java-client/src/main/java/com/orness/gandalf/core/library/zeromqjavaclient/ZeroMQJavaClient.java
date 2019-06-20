package com.orness.gandalf.core.library.zeromqjavaclient;

import com.orness.gandalf.core.library.zeromqjavaclient.command.ClientBusZeroMQ;
import com.orness.gandalf.core.library.zeromqjavaclient.command.ClientWorkflowEngineZeroMQ;
import com.orness.gandalf.core.library.zeromqjavaclient.event.SubscriberBusCallableZeroMQ;
import com.orness.gandalf.core.library.zeromqjavaclient.event.SubscriberBusZeroMQ;
import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ZeroMQJavaClient {


    public String connectionWorker;
    public String connectionSubscriber;
    private ClientBusZeroMQ clientBusZeroMQ;
    private ClientWorkflowEngineZeroMQ clientWorkflowEngineZeroMQ;
    private SubscriberBusZeroMQ subscriberBusZeroMQ;
    private ExecutorService executor;

    public ZeroMQJavaClient(String connectionWorker, String connectionSubscriber) {
        System.out.println("WO " + connectionWorker);
        System.out.println("SU " + connectionSubscriber);
        this.connectionWorker = connectionWorker;
        this.connectionSubscriber = connectionSubscriber;
        this.clientBusZeroMQ = new ClientBusZeroMQ(connectionWorker);
        this.clientWorkflowEngineZeroMQ = new ClientWorkflowEngineZeroMQ(connectionWorker);
        this.subscriberBusZeroMQ = new SubscriberBusZeroMQ(connectionSubscriber, "");
        this.executor = Executors.newFixedThreadPool(10);
    }

    public void createTopic(String topic) {
        this.clientBusZeroMQ.createTopic(topic);
    }

    public void deleteTopic(String topic) {
        this.clientBusZeroMQ.deleteTopic(topic);
    }

    public void sendMessageTopic(String topic, String message) {
        System.out.println("SEND CLIENT");
        System.out.println("SEND CLIENT " + topic);
        System.out.println("SEND CLIENT " + message);
        clientBusZeroMQ.sendMessageTopic(topic, message);
        //TODO ADD TOPIC DATABASE ???
    }

    public void sendMessageTopicDatabase(String topic, String message) {
        System.out.println("SEND CLIENT");
        System.out.println("SEND CLIENT " + topic);
        System.out.println("SEND CLIENT " + message);
        clientBusZeroMQ.sendMessageTopic(topic, message);
    }

    public void subscribeWorkflowEngineTopic(String topic) {
        this.clientWorkflowEngineZeroMQ.subscribeTopic(topic);
    }

    public void unsubscribeWorkflowEngineTopic(String topic) {
        this.clientWorkflowEngineZeroMQ.unsubscribeTopic(topic);
    }

    public MessageGandalf getMessageSubscriberBusTopic(String topic) {
         this.subscriberBusZeroMQ.initTopic(topic);
         return this.subscriberBusZeroMQ.getMessage();
    }

    public MessageGandalf getMessageSubscriberCallableBusTopic(String topic) {
        SubscriberBusCallableZeroMQ subscriberBusCallableZeroMQ = new SubscriberBusCallableZeroMQ(connectionSubscriber, topic);
        Future<MessageGandalf> futureMessageGandalf = executor.submit(subscriberBusCallableZeroMQ);
        MessageGandalf resultMessageGandalf = null;
        while(!futureMessageGandalf.isDone() && !futureMessageGandalf.isCancelled()) {
            System.out.println("Waiting...");
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (futureMessageGandalf.isDone() && !futureMessageGandalf.isCancelled()) {
            try {
                resultMessageGandalf = futureMessageGandalf.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return resultMessageGandalf;
    }
}
