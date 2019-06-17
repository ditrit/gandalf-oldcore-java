package com.orness.gandalf.core.connector.connectorworkflowengineservice.communication.event;

import com.google.gson.Gson;
import com.orness.gandalf.core.connector.connectorworkflowengineservice.workflow.ConnectorWorkflowEngine;
import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import com.orness.gandalf.core.module.zeromqmodule.event.subscriber.SubscriberZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

public class SubscriberWorkflowEngineZeroMQ extends SubscriberZeroMQ implements Runnable {

    @Autowired
    private ConnectorWorkflowEngine connectorWorkflowEngine;

    private String topic;
    private Gson mapper;

    public SubscriberWorkflowEngineZeroMQ( String connection, String topic) {
        super(connection);
        this.open(topic);
    }

    public void open(String topic) {
        super.open();
        this.topic = topic;
        this.mapper = new Gson();
        this.subscriber.subscribe(topic.getBytes());
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("RUNNNNN ");
            System.out.println("TOPIC RUN " + topic );
            String header = this.subscriber.recvStr();
            System.out.println("HEADER " + header);
            String content = this.subscriber.recvStr();
            System.out.println("content " + content);
            if(header.equals(this.topic)) {

                MessageGandalf messageGandalf = null;
                messageGandalf = mapper.fromJson(content, MessageGandalf.class);

                connectorWorkflowEngine.sendMessageWorkflowEngine(messageGandalf);
            }
        }
    }
}
