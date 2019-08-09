package com.orness.gandalf.core.module.zeromqmodule.event.subscriber;

import com.orness.gandalf.core.module.zeromqmodule.event.domain.EventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.domain.MessageEventZeroMQ;

public abstract class RunnableSubscriberZeroMQ extends SubscriberZeroMQ implements Runnable {

    private String topic;

    public RunnableSubscriberZeroMQ() {
        super();
        //this.topic = topic;
        //this.subscribe();
    }

    public void subscribe(String topic) {
        this.topic = topic;
        EventZeroMQ.subscribeEvent(this.subscriber,  this.topic);
    }

    protected abstract void sendMessageEventZeroMQ(MessageEventZeroMQ messageEventZeroMQ);

    @Override
    public void run() {
        System.out.println("IM RUNNING");
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println(subscriber);
            System.out.println(topic);
            MessageEventZeroMQ messageEventZeroMQ = EventZeroMQ.getEventByTopic(this.subscriber, this.topic);
            System.out.println("MESSAGE");
            System.out.println(messageEventZeroMQ);
            this.sendMessageEventZeroMQ(messageEventZeroMQ);
        }
    }
}
