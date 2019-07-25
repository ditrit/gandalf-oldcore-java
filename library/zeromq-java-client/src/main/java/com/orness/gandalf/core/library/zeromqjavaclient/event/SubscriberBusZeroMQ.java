package com.orness.gandalf.core.library.zeromqjavaclient.event;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.messagemodule.gandalf.domain.MessageGandalf;
import com.orness.gandalf.core.module.zeromqmodule.event.subscriber.SubscriberZeroMQ;

public class SubscriberBusZeroMQ extends SubscriberZeroMQ {

    private String topic;
    private Gson mapper;

    public SubscriberBusZeroMQ(String connection, String topic) {
        super(connection);
        this.open();
        this.mapper = new Gson();
        //this.initTopic(topic);
        this.topic = topic;
    }


    public void initTopic(String topic) {
        System.out.println("TOPIC " + topic);
        System.out.println("TOPIC 2 " + this.topic);
        if(!this.topic.equals(topic)) {
            if(this.topic.isEmpty()){
                this.subscriber.unsubscribe(this.topic);
            }
            this.topic = topic;
            this.subscriber.subscribe(topic.getBytes());
        }
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
