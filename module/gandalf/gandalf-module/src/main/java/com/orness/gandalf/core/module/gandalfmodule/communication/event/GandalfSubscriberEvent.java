package com.orness.gandalf.core.module.gandalfmodule.communication.event;

import com.orness.gandalf.core.module.zeromqmodule.event.domain.MessageEventZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.worker.RunnableWorkerEventZeroMQ;

import java.util.ArrayList;
import java.util.List;

public class GandalfSubscriberEvent extends RunnableSubscriberZeroMQ {

    private List<RunnableWorkerEventZeroMQ> runnableWorkerEventZeroMQList;
    private GandalfClientEvent gandalfClientEvent;
    private String connectionClient;
    private String connectionWorker;

    public GandalfSubscriberEvent(String topic, String connection, String connectionClient, String connectionWorker) {
        super();
        this.runnableWorkerEventZeroMQList = new ArrayList<>();
        this.connectionClient = connectionClient;
        this.connectionWorker = connectionWorker;

        //SUBS
        this.connect(connection);
        this.subscribe(topic);

        //CLIENT EVENT
        this.gandalfClientEvent = new GandalfClientEvent(connectionClient);
    }

    public List<RunnableWorkerEventZeroMQ> instantiateWorkers(List<RunnableWorkerEventZeroMQ> runnableWorkerEventZeroMQList) {
        this.runnableWorkerEventZeroMQList = runnableWorkerEventZeroMQList;
        for(RunnableWorkerEventZeroMQ runnableWorkerEventZeroMQ : this.runnableWorkerEventZeroMQList) {
            runnableWorkerEventZeroMQ.connect(this.connectionWorker);
        }
        return this.runnableWorkerEventZeroMQList;
    }

    public RunnableWorkerEventZeroMQ instantiateWorker(RunnableWorkerEventZeroMQ runnableWorkerEventZeroMQ) {
        runnableWorkerEventZeroMQ.connect(this.connectionWorker);
        this.runnableWorkerEventZeroMQList.add(runnableWorkerEventZeroMQ);
        return runnableWorkerEventZeroMQ;
    }

    @Override
    protected void sendMessageEventZeroMQ(MessageEventZeroMQ messageEventZeroMQ) {
        this.gandalfClientEvent.sendEventCommand(messageEventZeroMQ.getTypeEvent(), messageEventZeroMQ.getEvent());
    }
}
