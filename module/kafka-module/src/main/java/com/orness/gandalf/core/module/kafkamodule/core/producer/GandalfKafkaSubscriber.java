package com.orness.gandalf.core.module.kafkamodule.core.producer;

import com.orness.gandalf.core.module.kafkamodule.core.properties.KafkaProperties;
import com.orness.gandalf.core.module.zeromqmodule.event.domain.MessageEventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.subscriber.RunnableSubscriberZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

public class GandalfKafkaSubscriber extends RunnableSubscriberZeroMQ {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Autowired
    private KafkaProperties kafkaProperties;

    public GandalfKafkaSubscriber(String topic) {
        super();
        this.kafkaProducer = kafkaProducer;
        this.kafkaProperties = kafkaProperties;
        this.connect(topic, kafkaProperties.getWorker());
    }

    private void connect(String connection, String topic) {
        this.connect(connection);
        this.subscribe(topic);
    }

    @Override
    protected void sendMessageEventZeroMQ(MessageEventZeroMQ messageEventZeroMQ) {
        this.kafkaProducer.sendKafka(messageEventZeroMQ.getTopic(), messageEventZeroMQ);
    }
}
