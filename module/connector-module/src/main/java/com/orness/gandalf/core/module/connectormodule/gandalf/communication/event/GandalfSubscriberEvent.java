package com.orness.gandalf.core.module.connectormodule.gandalf.communication.event;

import com.orness.gandalf.core.module.zeromqmodule.event.domain.EventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.domain.MessageEventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.subscriber.SubscriberZeroMQ;

import java.util.HashMap;
import java.util.Map;

public class GandalfSubscriberEvent extends SubscriberZeroMQ implements Runnable  {

    private String topic;
    private Map<String, GandalfClientEvent> mapEventWorker; //<EventType, GandalfClientEvent>

    public GandalfSubscriberEvent(String connection, String topic) {
        super(connection);
        this.topic = topic;
        this.subscribe();
        this.mapEventWorker = new HashMap<>();
    }

    public void subscribe() {
        EventZeroMQ.subscribeEvent(this.subscriber,  this.topic);
    }

    private void sendMessageEventZeroMQ(MessageEventZeroMQ messageEventZeroMQ) {
        GandalfClientEvent gandalfClientEvent =  this.mapEventWorker.get(messageEventZeroMQ.getTypeEvent());
        if(gandalfClientEvent != null) {
            gandalfClientEvent.sendEventCommand(messageEventZeroMQ.getTypeEvent(), messageEventZeroMQ.getEvent());
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            MessageEventZeroMQ messageEventZeroMQ = EventZeroMQ.getEventByTopic(subscriber, topic);
            this.sendMessageEventZeroMQ(messageEventZeroMQ);
        }
    }
}
