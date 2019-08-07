package com.orness.gandalf.core.module.gandalfmodule.communication.command;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.gandalfmodule.manager.GandalfConnectorManager;
import com.orness.gandalf.core.module.gandalfmodule.properties.GandalfProperties;
import com.orness.gandalf.core.module.messagemodule.gandalf.domain.GandalfEvent;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.orness.gandalf.core.module.constantmodule.communication.CommunicationConstant.*;

@Component
public class GandalfWorkerCommand extends RunnableWorkerZeroMQ {

    private GandalfConnectorManager gandalfConnectorManager;
    private GandalfProperties gandalfProperties;
    private Gson mapper;

    @Autowired
    public GandalfWorkerCommand(GandalfConnectorManager gandalfConnectorManager, GandalfProperties gandalfProperties) {
        super();
        this.gandalfConnectorManager = gandalfConnectorManager;
        this.gandalfProperties = gandalfProperties;
        this.mapper = new Gson();
        this.connect(gandalfProperties.getWorker());
    }


    @Override
    public Object parse(String messageContent) {
        return this.mapper.fromJson(messageContent, GandalfEvent.class);
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

        System.out.println("ID " + this.identity);
        System.out.println("SENDER " + messageCommandZeroMQ.getSender());
        System.out.println("RECEIVER " + messageCommandZeroMQ.getReceiver());
        System.out.println("TYPE_COMMAND " + messageCommandZeroMQ.getTypeCommand());
        System.out.println("COMMAND " + messageCommandZeroMQ.getCommand());

        switch(messageCommandZeroMQ.getTypeCommand().toString()) {
            case COMMAND_START:
                this.gandalfConnectorManager.start();
                break;
            case COMMAND_STOP:
                this.gandalfConnectorManager.stop();
                break;
            case COMMAND_PUBLISH:
                this.gandalfConnectorManager.publish((GandalfEvent) this.parse(messageCommandZeroMQ.getCommand().toString()));
                break;
            case COMMAND_SUBSCRIBE:
                this.gandalfConnectorManager.subscribe("");
                break;
            case COMMAND_UNSUBSCRIBE:
                this.gandalfConnectorManager.unsubscribe("");
                break;
            default:
                //DO NOTHING
                break;
        }
    }
}
