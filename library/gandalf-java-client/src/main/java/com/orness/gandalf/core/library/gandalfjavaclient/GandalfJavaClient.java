package com.orness.gandalf.core.library.gandalfjavaclient;

import com.orness.gandalf.core.module.connectormodule.gandalf.communication.command.GandalfClientCommand;
import com.orness.gandalf.core.module.connectormodule.gandalf.communication.event.GandalfPublisherEvent;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TODO REVOIR COMMAND + CALLABLE / RUNNABLE
public class GandalfJavaClient {

    private String connectionWorker;
    private String connectionSubscriber;
    private ExecutorService executor;
    private GandalfClientCommand gandalfClientCommand;
    private GandalfPublisherEvent gandalfPublisherEvent;

    public GandalfJavaClient(String connectionWorker, String connectionSubscriber) {
        this.connectionWorker = connectionWorker;
        this.connectionSubscriber = connectionSubscriber;
        this.gandalfClientCommand = new GandalfClientCommand("");
        this.gandalfPublisherEvent = new GandalfPublisherEvent("");
        this.executor = Executors.newFixedThreadPool(10);
    }

    //CLIENT COMMAND
    public MessageCommandZeroMQ sendStartCommand() {
        return gandalfClientCommand.sendStartCommand();
    }

    public MessageCommandZeroMQ sendStopCommand() {
        return gandalfClientCommand.sendStopCommand();
    }

    public MessageCommandZeroMQ sendSubscriberCommand() {
        return gandalfClientCommand.sendSubscriberCommand();
    }

    public MessageCommandZeroMQ sendUnsubscribeCommand() {
        return gandalfClientCommand.sendUnsubscribeCommand();
    }

    //PUBLISHER EVENT
    public void sendEvent(String topic, String typeEvent, String event) {
        this.gandalfPublisherEvent.sendEvent(topic, typeEvent, event);
    }
}
