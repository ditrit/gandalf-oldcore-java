package com.orness.gandalf.core.connector.connectorgandalfservice.gandalf.communication.command;

import com.orness.gandalf.core.connector.connectorgandalfservice.gandalf.manager.ConnectorGandalfManager;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static com.orness.gandalf.core.module.constantmodule.communication.CommunicationConstant.*;
import static com.orness.gandalf.core.module.constantmodule.communication.CommunicationConstant.COMMAND_UNSUBSCRIBE;

@Component
@Scope("singleton")
public class ConnectorGandalfWorkerZeroMQ extends RunnableWorkerZeroMQ {

    @Autowired
    private ConnectorGandalfManager connectorGandalfManager;

    public ConnectorGandalfWorkerZeroMQ(@Value("${gandalf.communication.worker}") String connection) {
        super(connection);
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {
        System.out.println("ID " + this.identity);
        System.out.println("SENDER " + messageCommandZeroMQ.getSender());
        System.out.println("RECEIVER " + messageCommandZeroMQ.getReceiver());
        System.out.println("TYPE_COMMAND " + messageCommandZeroMQ.getTypeCommand());
        System.out.println("COMMAND " + messageCommandZeroMQ.getCommand());

        switch(messageCommandZeroMQ.getCommand().toString()) {
            case COMMAND_PUBLISH:
                this.connectorGandalfManager.publish();
                break;
            case COMMAND_SUBSCRIBE:
                connectorGandalfManager.subscribe();
                break;
            case COMMAND_UNSUBSCRIBE:
                connectorGandalfManager.unsubscribe();
                break;
            default:
                //DO NOTHING
                break;
        }
    }
}
