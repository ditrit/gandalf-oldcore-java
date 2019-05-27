package com.orness.gandalf.core.connector.connectorbusservice.consumer;

import org.apache.kafka.common.serialization.StringDeserializer;
import com.orness.gandalf.core.module.messagebusmodule.domain.MessageBus;
import com.orness.gandalf.core.module.subscribertopicmodule.domain.Topic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

public class ConnectorBusConsumer {

    private final String brokerAddress;
    private final Topic topic;

    public ConnectorBusConsumer(Topic topic) {
        this.brokerAddress = "localhost:9092";
        this.topic = topic;
        this.start();
    }

    void start() {
        MessageListener<String, MessageBus> messageListener = record -> this.topic.getMessageBusLinkedList().add(record.value());
        ConcurrentMessageListenerContainer container = new ConcurrentMessageListenerContainer<>(consumerFactory(brokerAddress), containerProperties(messageListener));
        container.start();
    }

    private ConsumerFactory<String, MessageBus> consumerFactory(String brokerAddress) {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(brokerAddress), new StringDeserializer(), new JsonDeserializer<>(MessageBus.class));
    }

    private ContainerProperties containerProperties(MessageListener<String, MessageBus> messageListener) {
        ContainerProperties containerProperties = new ContainerProperties(this.topic.getName());
        containerProperties.setMessageListener(messageListener);
        return containerProperties;
    }

    private Map<String, Object> consumerConfig(String brokerAddress) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "toto");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }

}
