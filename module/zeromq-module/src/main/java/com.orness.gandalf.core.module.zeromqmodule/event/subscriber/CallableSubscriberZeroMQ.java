package com.orness.gandalf.core.module.zeromqmodule.event.subscriber;

import com.orness.gandalf.core.module.zeromqmodule.event.domain.EventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.domain.MessageEventZeroMQ;

import java.util.concurrent.Callable;
//TODO REVOIR
public abstract class CallableSubscriberZeroMQ extends SubscriberZeroMQ implements Callable<MessageEventZeroMQ> {

    protected String topic;

    public CallableSubscriberZeroMQ(String connection, String topic) {
        super();
        this.topic = topic;
        this.subscribe();
        this.connect(connection);
    }

    public void subscribe() {
        EventZeroMQ.subscribeEvent(this.subscriber,  this.topic);
    }

    public abstract MessageEventZeroMQ call();
}
