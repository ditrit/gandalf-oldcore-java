package com.orness.gandalf.core.module.kafkamodule.core.consumer.gandalf;

import com.orness.gandalf.core.library.gandalfjavaclient.core.GandalfPublisherZeroMQ;
import com.orness.gandalf.core.module.busmodule.properties.ConnectorBusProperties;
import com.orness.gandalf.core.module.kafkamodule.core.consumer.KafkaConsumer;
import com.orness.gandalf.core.module.kafkamodule.properties.ConnectorKafkaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "kafka")
public class KafkaGandalfEventConsumer extends KafkaConsumer {

    private GandalfPublisherZeroMQ gandalfPublisherZeroMQ;

    @Autowired
    public KafkaGandalfEventConsumer(GandalfPublisherZeroMQ gandalfPublisherZeroMQ, ConnectorKafkaProperties connectorKafkaProperties, ConnectorBusProperties connectorBusProperties) {
        super(connectorKafkaProperties, connectorBusProperties);
        this.gandalfPublisherZeroMQ = gandalfPublisherZeroMQ;
    }

    @Override
    protected void publish(Object message) {
        KafkaGandalfMessage event = (KafkaGandalfMessage) message;
        this.gandalfPublisherZeroMQ.sendEvent(event.getTopic(), event.getEvent(), event.getPayload());
    }

    @Override
    protected Object parse(String value) {
        return this.mapper.fromJson(value, KafkaGandalfMessage.class);
    }
}
