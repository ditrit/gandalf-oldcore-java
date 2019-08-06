package com.orness.gandalf.core.library.gandalfjavaclient;

import com.orness.gandalf.core.module.gandalfmodule.communication.command.GandalfClientCommand;
import com.orness.gandalf.core.module.gandalfmodule.communication.event.GandalfPublisherEvent;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class GandalfJavaClient {

    private ExecutorService executor;
    private GandalfClientCommand gandalfClientCommand;
    private GandalfPublisherEvent gandalfPublisherEvent;

    @Autowired
    public GandalfJavaClient(GandalfClientCommand gandalfClientCommand, GandalfPublisherEvent gandalfPublisherEvent) {
        this.gandalfClientCommand = gandalfClientCommand;
        this.gandalfPublisherEvent = gandalfPublisherEvent;
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
