package com.ditrit.gandalf.gandalfjava.functions.functionskafka.core.consumer;

import com.ditrit.gandalf.gandalfjava.library.clientgandalf.ClientGandalf;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.core.consumer.core.RunnableKafkaConsumer;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.properties.ConnectorKafkaProperties;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(ConnectorKafkaProperties.class)
public class FunctionKafkaConsumer extends RunnableKafkaConsumer {

    private ClientGandalf clientGandalf;
    private ConnectorKafkaProperties connectorKafkaProperties;
    protected Gson mapper;

    @Autowired
    public FunctionKafkaConsumer(ClientGandalf clientGandalf, ConnectorKafkaProperties connectorKafkaProperties) {
        super();
        this.clientGandalf = clientGandalf;
        this.connectorKafkaProperties = connectorKafkaProperties;
        this.mapper = new Gson();
        this.initRunnable(this.connectorKafkaProperties.getEndPointConnection(), this.connectorKafkaProperties.getGroup(), this.connectorKafkaProperties.getSynchronizeTopics());
    }

    @Override
    protected void publish(Object message) {
        GandalfKafkaMessage event = (GandalfKafkaMessage) message;
        this.clientGandalf.getClient().sendEvent(event.getTopic(), event.getEvent(), event.getTimeout(), event.getPayload());
    }

    @Override
    protected Object parse(String value) {
        return this.mapper.fromJson(value, GandalfKafkaMessage.class);
    }
}
