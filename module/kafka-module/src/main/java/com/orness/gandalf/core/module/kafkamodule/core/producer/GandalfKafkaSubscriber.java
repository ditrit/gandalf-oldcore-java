package com.orness.gandalf.core.module.kafkamodule.core.producer;

import com.orness.gandalf.core.module.kafkamodule.core.properties.KafkaProperties;
import com.orness.gandalf.core.module.zeromqmodule.event.domain.MessageEventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.subscriber.RunnableSubscriberZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

public class GandalfKafkaSubscriber extends RunnableSubscriberZeroMQ {

    private KafkaProducer kafkaProducer;
    private KafkaProperties kafkaProperties;

    @Autowired
    public GandalfKafkaSubscriber(String topic, KafkaProducer kafkaProducer, KafkaProperties kafkaProperties) {
        super(topic);
        this.kafkaProducer = kafkaProducer;
        this.kafkaProperties = kafkaProperties;
        this.connect(kafkaProperties.getAddress());
    }

    @Override
    protected void sendMessageEventZeroMQ(MessageEventZeroMQ messageEventZeroMQ) {
        this.kafkaProducer.sendKafka(messageEventZeroMQ.getTopic(), messageEventZeroMQ);
    }
}
