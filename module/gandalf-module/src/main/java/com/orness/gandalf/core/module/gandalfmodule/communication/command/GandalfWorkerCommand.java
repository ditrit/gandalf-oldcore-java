package com.orness.gandalf.core.module.gandalfmodule.communication.command;

import com.orness.gandalf.core.module.gandalfmodule.manager.GandalfConnectorManager;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import static com.orness.gandalf.core.module.constantmodule.communication.CommunicationConstant.*;

public class GandalfWorkerCommand extends RunnableWorkerZeroMQ {

    @Autowired
    private GandalfConnectorManager gandalfConnectorManager;

    public GandalfWorkerCommand(@Value("${gandalf.communication.worker}") String connection) {
        super(connection);
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
                this.gandalfConnectorManager.publish(messageCommandZeroMQ.getCommand().toString());
                break;
            case COMMAND_SUBSCRIBE:
                this.gandalfConnectorManager.subscribe();
                break;
            case COMMAND_UNSUBSCRIBE:
                this.gandalfConnectorManager.unsubscribe();
                break;
            default:
                //DO NOTHING
                break;
        }
    }
}
