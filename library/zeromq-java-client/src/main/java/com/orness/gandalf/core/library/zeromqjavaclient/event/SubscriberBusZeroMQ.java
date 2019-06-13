package com.orness.gandalf.core.library.zeromqjavaclient.event;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import com.orness.gandalf.core.module.zeromqmodule.event.subscriber.SubscriberZeroMQ;

public class SubscriberBusZeroMQ extends SubscriberZeroMQ {

    private String topic;
    private Gson mapper;

    public SubscriberBusZeroMQ(String connection) {
        super(connection);
    }

    public void open(String topic) {
        super.open();
        this.topic = topic;
        this.mapper = new Gson();
        this.subscriber.subscribe(topic.getBytes());
    }

    public MessageGandalf getMessage() {

        String header = this.subscriber.recvStr();
        System.out.println("HEADER " + header);
        String content = this.subscriber.recvStr();
        System.out.println("content " + content);
        if(header.equals(this.topic)) {

            MessageGandalf messageGandalf = null;
            messageGandalf = mapper.fromJson(content, MessageGandalf.class);

            return messageGandalf;
        }
        return null;
    }
}
