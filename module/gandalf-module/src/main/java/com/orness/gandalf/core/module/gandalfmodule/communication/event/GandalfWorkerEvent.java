package com.orness.gandalf.core.module.gandalfmodule.communication.event;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.gandalfmodule.manager.GandalfConnectorManager;
import com.orness.gandalf.core.module.gandalfmodule.properties.GandalfProperties;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.CommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.subscriber.RunnableSubscriberWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class GandalfWorkerEvent extends RunnableWorkerZeroMQ {

    private GandalfConnectorManager gandalfConnectorManager;
    private GandalfProperties gandalfProperties;

    public GandalfWorkerEvent(GandalfConnectorManager gandalfConnectorManager, GandalfProperties gandalfProperties) {
        super();
        this.gandalfConnectorManager = gandalfConnectorManager;
        this.gandalfProperties = gandalfProperties;
        this.mapper = new Gson();
        this.connect(gandalfProperties.getWorker());
    }

    @Override
    public Object parse(String messageContent) {
        return null;
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

        System.out.println("ID " + this.identity);
        System.out.println("SENDER " + messageCommandZeroMQ.getSender());
        System.out.println("RECEIVER " + messageCommandZeroMQ.getReceiver());
        System.out.println("TYPE_COMMAND " + messageCommandZeroMQ.getTypeCommand());
        System.out.println("COMMAND " + messageCommandZeroMQ.getCommand());

    }
}
