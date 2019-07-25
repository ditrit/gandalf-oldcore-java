package com.orness.gandalf.core.module.kafkamodule.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;
//TODO REVOIR PROP
public class KafkaConsumer implements Runnable {

    @Value("${gandalf.bus.broker}")
    private String brokerAddress;

    @Value("${gandalf.communication.publisher}")
    private String connection;

    @Value("${gandalf.bus.group}")
    private String group;

    private final String topic;

    public KafkaConsumer(String topic) {
        this.topic = topic;
        //this.start();
    }

    //TODO REVOIR
    public void run() {
        MessageListener<String, String> messageListener = record -> this.publish(record.value());
        ConcurrentMessageListenerContainer container = new ConcurrentMessageListenerContainer<>(consumerFactory(brokerAddress), containerProperties(messageListener));
        container.start();
    }

    //TODO REVOIR
    private void publish(String value) {
        System.out.println(value);
//        if(messageGandalf != null) {
//            this.busPublisherZeroMQ.sendMessageTopic(topic, messageGandalf.toJson());
//            System.out.println(topic + " " + messageGandalf.toString());
//        }
    }

    private ConsumerFactory<String, String> consumerFactory(String brokerAddress) {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(brokerAddress), new StringDeserializer(), new StringDeserializer());
    }

    private ContainerProperties containerProperties(MessageListener<String, String> messageListener) {
        ContainerProperties containerProperties = new ContainerProperties(this.topic);
        containerProperties.setMessageListener(messageListener);
        return containerProperties;
    }

    private Map<String, Object> consumerConfig(String brokerAddress) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, group);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

}
