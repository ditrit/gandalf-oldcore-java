package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.core.consumer;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.properties.ConnectorKafkaProperties;
import com.google.gson.Gson;
import com.ditrit.gandalf.core.clientcore.library.LibraryClient;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.core.consumer.core.RunnableKafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(ConnectorKafkaProperties.class)
public class ConnectorKafkaConsumer extends RunnableKafkaConsumer {

    private LibraryClient gandalfClient;
    private ConnectorKafkaProperties connectorKafkaProperties;
    protected Gson mapper;

    @Autowired
    public ConnectorKafkaConsumer(LibraryClient gandalfClient, ConnectorKafkaProperties connectorKafkaProperties) {
        super();
        this.gandalfClient = gandalfClient;
        this.connectorKafkaProperties = connectorKafkaProperties;
        this.mapper = new Gson();
        this.initRunnable(this.connectorKafkaProperties.getEndPointConnection(), this.connectorKafkaProperties.getGroup(), this.connectorKafkaProperties.getSynchronizeTopics());
    }

    @Override
    protected void publish(Object message) {
        GandalfKafkaMessage event = (GandalfKafkaMessage) message;
        this.gandalfClient.sendEvent(event.getTopic(), event.getEvent(), event.getTimeout(), event.getPayload());
    }

    @Override
    protected Object parse(String value) {
        return this.mapper.fromJson(value, GandalfKafkaMessage.class);
    }
}
