package com.orness.gandalf.core.module.gandalfmodule.communication.event;

import com.orness.gandalf.core.module.zeromqmodule.event.domain.MessageEventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.subscriber.RunnableSubscriberZeroMQ;

import java.util.ArrayList;
import java.util.List;

public class GandalfSubscriberEvent extends RunnableSubscriberZeroMQ {

    private List<GandalfClientEvent> listGandalfClientEvent;
    private List<GandalfWorkerEvent> listGandalfWorkerEvent;
    private GandalfClientEvent gandalfClientEvent;
    private String connectionClient;
    private String connectionWorker;

    public GandalfSubscriberEvent(String topic, String connection, String connectionClient, String connectionWorker) {
        super();
        this.listGandalfClientEvent = new ArrayList<>();
        this.listGandalfWorkerEvent = new ArrayList<>();
        this.connectionClient = connectionClient;
        this.connectionWorker = connectionWorker;

        //SUBS
        this.connect(connection);
        this.subscribe(topic);

        //CLIENT EVENT
        this.gandalfClientEvent = new GandalfClientEvent(connectionClient);
    }

    public void addWorker(GandalfWorkerEvent gandalfWorkerEvent) {
        gandalfWorkerEvent.bind(this.connectionWorker);
        this.listGandalfWorkerEvent.add(gandalfWorkerEvent);
    }

    @Override
    protected void sendMessageEventZeroMQ(MessageEventZeroMQ messageEventZeroMQ) {
        //TODO VERIFICATION WORKER
        this.gandalfClientEvent.sendEventCommand(messageEventZeroMQ.getTypeEvent(), messageEventZeroMQ.getEvent());
    }
}
