/*
package com.orness.gandalf.core.connector.connectorbusservice.other.kafka.consumer;

import com.orness.gandalf.core.connector.connectorbusservice.other.kafka.communication.event.BusPublisherZeroMQ;
import com.orness.gandalf.core.module.messagemodule.gandalf.domain.GandalfMessage;
import org.apache.kafka.common.serialization.StringDeserializer;
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

public class ConnectorBusConsumer implements Runnable {

    @Value("${gandalf.bus.broker}")
    private String brokerAddress;

    @Value("${gandalf.communication.publisher}")
    private String connection;

    @Value("${gandalf.bus.group}")
    private String group;

    private final String topic;
    private BusPublisherZeroMQ busPublisherZeroMQ;

    public ConnectorBusConsumer(String topic) {
        this.topic = topic;
        //this.start();
    }

    public void run() {
        busPublisherZeroMQ = new BusPublisherZeroMQ(connection);

        MessageListener<String, GandalfMessage> messageListener = record -> this.publish(record.value());
        ConcurrentMessageListenerContainer container = new ConcurrentMessageListenerContainer<>(consumerFactory(brokerAddress), containerProperties(messageListener));
        container.start();
    }

    private void publish(GandalfMessage gandalfMessage) {
        System.out.println(gandalfMessage);
        if(gandalfMessage != null) {
            this.busPublisherZeroMQ.sendMessageTopic(topic, gandalfMessage.toJson());
            System.out.println(topic + " " + gandalfMessage.toString());
        }
    }

    private ConsumerFactory<String, GandalfMessage> consumerFactory(String brokerAddress) {
        return new DefaultKafkaConsumerFactory<>(consumerConfig(brokerAddress), new StringDeserializer(), new JsonDeserializer<>(GandalfMessage.class));
    }

    private ContainerProperties containerProperties(MessageListener<String, GandalfMessage> messageListener) {
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
*/