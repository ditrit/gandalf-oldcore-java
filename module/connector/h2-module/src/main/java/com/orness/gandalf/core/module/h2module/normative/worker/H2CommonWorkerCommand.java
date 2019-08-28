package com.orness.gandalf.core.module.h2module.normative.worker;

import com.orness.gandalf.core.module.h2module.normative.manager.H2CommonManager;
import com.orness.gandalf.core.module.h2module.core.H2Command;
import com.orness.gandalf.core.module.h2module.core.properties.H2Properties;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.event.worker.RunnableWorkerEventZeroMQ;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.orness.gandalf.core.module.databasemodule.constant.DatabaseConstant.*;

@Component(value = "commonWorkerCommand")
@Profile(value = "h2-module")
public class H2CommonWorkerCommand extends RunnableWorkerEventZeroMQ {

    private H2CommonManager h2CommonManager;
    private H2Properties h2Properties;

    public H2CommonWorkerCommand(H2CommonManager h2CommonManager, H2Properties h2Properties) {
        super();
        this.h2CommonManager = h2CommonManager;
        this.h2Properties = h2Properties;
        this.connect(h2Properties.getWorker());
    }

    @Override
    public Object parse(String messageContent) {
        return this.mapper.fromJson(messageContent, H2Command.class);
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

        System.out.println("ID " + this.identity);
        System.out.println("SENDER " + messageCommandZeroMQ.getSender());
        System.out.println("RECEIVER " + messageCommandZeroMQ.getReceiver());
        System.out.println("TYPE_COMMAND " + messageCommandZeroMQ.getTypeCommand());
        System.out.println("COMMAND " + messageCommandZeroMQ.getCommand());

        //TODO REVOIR ARGS
        switch(messageCommandZeroMQ.getTypeCommand().toString()) {
            case COMMAND_LIST:
                this.h2CommonManager.list(null);
                break;
            case COMMAND_SELECT:
                this.h2CommonManager.select(null ,0L);
                break;
            case COMMAND_INSERT:
                this.h2CommonManager.insert(null,"");
                break;
            case COMMAND_UPDATE:
                this.h2CommonManager.update(null, 0L, "");
                break;
            case COMMAND_DELETE:
                this.h2CommonManager.delete(null, 0L);
                break;
            default:
                //DO NOTHING
                break;
        }
    }
}
