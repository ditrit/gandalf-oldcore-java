package com.orness.gandalf.core.module.gandalfmodule.communication.event;

import com.orness.gandalf.core.module.gandalfmodule.properties.GandalfProperties;
import com.orness.gandalf.core.module.zeromqmodule.event.domain.MessageEventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.subscriber.RunnableSubscriberZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

public class GandalfSubscriberEvent extends RunnableSubscriberZeroMQ {

    private GandalfProperties gandalfProperties;
    private Map<String, GandalfClientEvent> mapEventWorker; //<EventType, GandalfClientEvent>

    @Autowired
    public GandalfSubscriberEvent(GandalfProperties gandalfProperties, String topic) {
        super();
        this.gandalfProperties = gandalfProperties;
        this.mapEventWorker = new HashMap<>();
        this.subscribe(gandalfProperties.getSubscriber());
    }

    @Override
    protected void sendMessageEventZeroMQ(MessageEventZeroMQ messageEventZeroMQ) {
        GandalfClientEvent gandalfClientEvent =  this.mapEventWorker.get(messageEventZeroMQ.getTypeEvent());
        if(gandalfClientEvent != null) {
            gandalfClientEvent.sendEventCommand(messageEventZeroMQ.getTypeEvent(), messageEventZeroMQ.getEvent());
        }
    }
}
