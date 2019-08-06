package com.orness.gandalf.core.module.zeromqmodule.event.subscriber;

import com.orness.gandalf.core.module.zeromqmodule.event.domain.EventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.domain.MessageEventZeroMQ;

public abstract class RunnableSubscriberZeroMQ extends SubscriberZeroMQ implements Runnable {

    protected String topic;

    public RunnableSubscriberZeroMQ() {
        super();
        //this.topic = topic;
        //this.subscribe();
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void subscribe(String topic) {
        this.setTopic(topic);
        EventZeroMQ.subscribeEvent(this.subscriber,  this.topic);
    }

    protected abstract void sendMessageEventZeroMQ(MessageEventZeroMQ messageEventZeroMQ);

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            MessageEventZeroMQ messageEventZeroMQ = EventZeroMQ.getEventByTopic(this.subscriber, this.topic);
            this.sendMessageEventZeroMQ(messageEventZeroMQ);
        }
    }
}
