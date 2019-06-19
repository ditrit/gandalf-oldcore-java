package com.orness.gandalf.core.library.zeromqjavaclient.event;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import com.orness.gandalf.core.module.zeromqmodule.event.subscriber.SubscriberZeroMQ;

import java.util.concurrent.Callable;

public class SubscriberBusCallableZeroMQ extends SubscriberZeroMQ implements Callable<MessageGandalf> {

    private String topic;
    private Gson mapper;

    public SubscriberBusCallableZeroMQ(String connection, String topic) {
        super(connection);
        this.open(topic);
    }

    public void open(String topic) {
        super.open();
        this.topic = topic;
        this.mapper = new Gson();
        this.subscriber.subscribe(topic.getBytes());
    }

    public MessageGandalf call() {
        MessageGandalf messageGandalf = null;

        while (messageGandalf == null) {
            String header = this.subscriber.recvStr();
            System.out.println("HEADER " + header);
            String content = this.subscriber.recvStr();
            System.out.println("content " + content);
            if(header.equals(this.topic)) {
                messageGandalf = mapper.fromJson(content, MessageGandalf.class);
                break;
            }
        }
        return messageGandalf;
    }
}
