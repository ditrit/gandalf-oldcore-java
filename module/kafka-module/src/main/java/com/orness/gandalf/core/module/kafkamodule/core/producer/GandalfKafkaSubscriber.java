package com.orness.gandalf.core.module.kafkamodule.core.producer;

import com.orness.gandalf.core.module.zeromqmodule.event.domain.MessageEventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.subscriber.RunnableSubscriberZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

public class GandalfKafkaSubscriber extends RunnableSubscriberZeroMQ {

    @Autowired
    private KafkaProducer kafkaProducer;

    public GandalfKafkaSubscriber(String connection, String topic) {
        super(connection, topic);
    }

    @Override
    protected void sendMessageEventZeroMQ(MessageEventZeroMQ messageEventZeroMQ) {
        this.kafkaProducer.sendKafka(messageEventZeroMQ.getTopic(), messageEventZeroMQ);
    }
}
