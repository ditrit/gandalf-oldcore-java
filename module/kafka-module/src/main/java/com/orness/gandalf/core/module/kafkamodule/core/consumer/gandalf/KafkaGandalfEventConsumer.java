package com.orness.gandalf.core.module.kafkamodule.core.consumer.gandalf;

import com.orness.gandalf.core.module.gandalfmodule.communication.event.GandalfPublisherEvent;
import com.orness.gandalf.core.module.kafkamodule.core.consumer.KafkaConsumer;
import com.orness.gandalf.core.module.messagemodule.gandalf.domain.GandalfEvent;

public class KafkaGandalfEventConsumer extends KafkaConsumer {

    private GandalfPublisherEvent gandalfPublisherEvent;

    public KafkaGandalfEventConsumer(String topic) {
        super(topic);
        this.gandalfPublisherEvent = new GandalfPublisherEvent("");
    }

    @Override
    protected void publish(Object message) {
        GandalfEvent event = (GandalfEvent) message;
        this.gandalfPublisherEvent.sendEvent(event.getTopic(), event.getTypeEvent(), event.getEvent());
    }

    @Override
    protected Object parse(String value) {
        return this.mapper.fromJson(value, GandalfEvent.class);
    }
}
