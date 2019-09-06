package com.orness.gandalf.core.module.kafkamodule.core.consumer;

import com.google.gson.Gson;
import com.orness.gandalf.core.library.gandalfjavaclient.GandalfJavaClient;
import com.orness.gandalf.core.module.busmodule.properties.ConnectorBusProperties;
import com.orness.gandalf.core.module.kafkamodule.core.consumer.core.RunnableKafkaConsumer;
import com.orness.gandalf.core.module.kafkamodule.properties.ConnectorKafkaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "kafka")
public class ConnectorKafkaConsumer extends RunnableKafkaConsumer {

    private GandalfJavaClient gandalfJavaClient;
    private ConnectorKafkaProperties connectorKafkaProperties;
    protected Gson mapper;

    @Autowired
    public ConnectorKafkaConsumer(GandalfJavaClient gandalfJavaClient, ConnectorKafkaProperties connectorKafkaProperties) {
        super();
        this.gandalfJavaClient = gandalfJavaClient;
        this.connectorKafkaProperties = connectorKafkaProperties;
        this.mapper = new Gson();
        this.initRunnable(this.connectorKafkaProperties.getEndPointConnection(), this.connectorKafkaProperties.getGroup(), this.connectorKafkaProperties.getSynchronizeTopics());
    }

    @Override
    protected void publish(Object message) {
        GandalfKafkaMessage event = (GandalfKafkaMessage) message;
        this.gandalfJavaClient.sendEvent(event.getTopic(), event.getEvent(), event.getPayload());
    }

    @Override
    protected Object parse(String value) {
        return this.mapper.fromJson(value, GandalfKafkaMessage.class);
    }
}
