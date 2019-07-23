package com.orness.gandalf.core.module.connectormodule.gandalf.communication.event;

import com.orness.gandalf.core.module.zeromqmodule.event.domain.EventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.domain.MessageEventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.subscriber.SubscriberZeroMQ;

import java.util.Map;

public class SubscriberGandalfEvent extends SubscriberZeroMQ implements Runnable  {

    private String topic;
    //TODO
    private ClientGandalfEvent clientGandalfEvent;
    //TODO
    private Map<String, String> mapEventWorker;

    public SubscriberGandalfEvent(String connection, String topic) {
        super(connection);
        this.open(topic);
    }

    public void open(String topic) {
        super.open();
        this.topic = topic;
        EventZeroMQ.subscribeEvent(subscriber, topic);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            MessageEventZeroMQ messageEventZeroMQ = EventZeroMQ.getEventByTopic(subscriber, topic);
            //CALL WORKER GANDALF
        }
    }
}
