package com.orness.gandalf.core.connector.connectorbusservice.consumer;

import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import com.orness.gandalf.core.module.zeromqmodule.event.publisher.PublisherZeroMQ;
import org.apache.kafka.common.serialization.StringDeserializer;
import com.orness.gandalf.core.module.subscribertopicmodule.domain.Topic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

public class ConnectorBusConsumer {

    @Value("${gandalf.bus.broker}")
    private String brokerAddress;

    @Value("${gandalf.bus.publisher}")
    private String connection;

    @Value("${gandalf.bus.group}")
    private String group;

    private final Topic topic;
    private PublisherZeroMQ publisherZeroMQ;

    public ConnectorBusConsumer(Topic topic) {
        //this.brokerAddress = "localhost:9092";
        //this.connection = "ipc://pub";
        this.topic = topic;
        //this.connection = "tcp://*:11001";
        //publisherZeroMQ = new PublisherZeroMQ(connection);
        this.start();
    }

    void start() {
        publisherZeroMQ = new PublisherZeroMQ(connection);

        MessageListener<String, MessageGandalf> messageListener = record -> this.publish(record.value());
        ConcurrentMessageListenerContainer container = new ConcurrentMessageListenerContainer<>(consumerFactory(brokerAddress), containerProperties(messageListener));
        container.start();
    }

    private void publish(MessageGandalf messageGandalf) {
        System.out.println(messageGandalf);
        if(messageGandalf != null) {
            //TOPIC
            this.publisherZeroMQ.sendTopic(topic.getName());
            //DATA
            //this.publisherZeroMQ.getPublisher().send(messageGandalf.toString());
            this.publisherZeroMQ.sendMessage(messageGandalf.toJson());
            //PRINT

            System.out.println(topic + " " + messageGandalf.toString());
        }
    }

    private ConsumerFactory<String, MessageGandalf> consumerFactory(String brokerAddress) {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(brokerAddress), new StringDeserializer(), new JsonDeserializer<>(MessageGandalf.class));
    }

    private ContainerProperties containerProperties(MessageListener<String, MessageGandalf> messageListener) {
        ContainerProperties containerProperties = new ContainerProperties(this.topic.getName());
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
