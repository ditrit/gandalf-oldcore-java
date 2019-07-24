package com.orness.gandalf.core.library.zeromqjavaclient.event;

import com.orness.gandalf.core.module.zeromqmodule.event.domain.EventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.domain.MessageEventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.subscriber.CallableSubscriberZeroMQ;

public class SubscriberBusCallableZeroMQ extends CallableSubscriberZeroMQ {

    public SubscriberBusCallableZeroMQ(String connection, String topic) {
        super(connection, topic);
    }

    @Override
    public MessageEventZeroMQ call() {
        MessageEventZeroMQ messageEventZeroMQ = null;
        while (messageEventZeroMQ == null) {
            messageEventZeroMQ = EventZeroMQ.getEventByTopic(this.subscriber, this.topic);
        }
        return messageEventZeroMQ;
    }
}
