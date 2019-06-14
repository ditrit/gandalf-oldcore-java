package com.orness.gandalf.core.library.zeromqjavaclient;

import com.orness.gandalf.core.library.zeromqjavaclient.command.ClientBusZeroMQ;
import com.orness.gandalf.core.library.zeromqjavaclient.command.ClientWorkflowEngineZeroMQ;
import com.orness.gandalf.core.library.zeromqjavaclient.event.SubscriberBusCallableZeroMQ;
import com.orness.gandalf.core.library.zeromqjavaclient.event.SubscriberBusZeroMQ;
import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;

public class ZeroMQJavaClient {


    public String connection;
    private ClientBusZeroMQ clientBusZeroMQ;
    private ClientWorkflowEngineZeroMQ clientWorkflowEngineZeroMQ;
    private SubscriberBusZeroMQ subscriberBusZeroMQ;

    public ZeroMQJavaClient(String connection) {
        this.connection = connection;
        this.clientBusZeroMQ = new ClientBusZeroMQ(connection);
        this.clientWorkflowEngineZeroMQ = new ClientWorkflowEngineZeroMQ(connection);
        this.subscriberBusZeroMQ = new SubscriberBusZeroMQ(connection, "");
    }

    public void createTopic(String topic) {
        this.clientBusZeroMQ.createTopic(topic);
    }

    public void deleteTopic(String topic) {
        this.clientBusZeroMQ.deleteTopic(topic);
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

    public SubscriberBusCallableZeroMQ subscribeBusCallableTopic(String topic) {
        return new SubscriberBusCallableZeroMQ(connection, topic);
    }
}
