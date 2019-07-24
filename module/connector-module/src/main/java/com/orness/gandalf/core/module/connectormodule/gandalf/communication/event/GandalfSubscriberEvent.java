package com.orness.gandalf.core.module.connectormodule.gandalf.communication.event;

import com.orness.gandalf.core.module.zeromqmodule.event.domain.EventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.domain.MessageEventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.subscriber.RunnableSubscriberZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.subscriber.SubscriberZeroMQ;

import java.util.HashMap;
import java.util.Map;

public class GandalfSubscriberEvent extends RunnableSubscriberZeroMQ {

    private Map<String, GandalfClientEvent> mapEventWorker; //<EventType, GandalfClientEvent>

    public GandalfSubscriberEvent(String connection, String topic) {
        super(connection, topic);
        this.mapEventWorker = new HashMap<>();
    }

    @Override
    protected void sendMessageEventZeroMQ(MessageEventZeroMQ messageEventZeroMQ) {
        GandalfClientEvent gandalfClientEvent =  this.mapEventWorker.get(messageEventZeroMQ.getTypeEvent());
        if(gandalfClientEvent != null) {
            gandalfClientEvent.sendEventCommand(messageEventZeroMQ.getTypeEvent(), messageEventZeroMQ.getEvent());
        }
    }
}
