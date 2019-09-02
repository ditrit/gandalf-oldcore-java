package com.orness.gandalf.core.module.kafkamodule.core.consumer;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.busmodule.properties.ConnectorBusProperties;
import com.orness.gandalf.core.module.kafkamodule.properties.ConnectorKafkaProperties;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class KafkaConsumer implements Runnable {

    private ConnectorBusProperties connectorBusProperties;
    private ConnectorKafkaProperties connectorKafkaProperties;
    private List<String> topics;
    protected Gson mapper;

    public KafkaConsumer(ConnectorKafkaProperties connectorKafkaProperties, ConnectorBusProperties connectorBusProperties) {
        this.connectorKafkaProperties = connectorKafkaProperties;
        this.connectorBusProperties = connectorBusProperties;
        this.topics = connectorKafkaProperties.getSynchronizeTopics();
        this.mapper = new Gson();
    }

    public void run() {
        MessageListener<String, String> messageListener = record -> this.publish(this.parse(record.value()));
        ConcurrentMessageListenerContainer container = new ConcurrentMessageListenerContainer<>(this.consumerFactory(connectorBusProperties.getBusConnection()), this.containerProperties(messageListener));
        container.start();
    }

    protected abstract void publish(Object message);

    protected abstract Object parse(String value);

    private ConsumerFactory<String, String> consumerFactory(String brokerAddress) {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(brokerAddress), new StringDeserializer(), new StringDeserializer());
    }

    private ContainerProperties containerProperties(MessageListener<String, String> messageListener) {
        ContainerProperties containerProperties = new ContainerProperties(this.topics.toArray().toString());
        containerProperties.setMessageListener(messageListener);
        return containerProperties;
    }

    private Map<String, Object> consumerConfig(String brokerAddress) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, connectorKafkaProperties.getGroup());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return props;
    }
}
