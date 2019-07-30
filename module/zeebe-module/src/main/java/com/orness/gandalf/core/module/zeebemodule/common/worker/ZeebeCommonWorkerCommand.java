package com.orness.gandalf.core.module.zeebemodule.common.worker;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.zeebemodule.common.manager.ZeebeCommonManager;
import com.orness.gandalf.core.module.zeebemodule.core.ZeebeCommand;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

import static com.orness.gandalf.core.module.workflowenginemodule.common.constant.WorkflowEngineConstant.*;

//TODO REVOIR ARGS
public class ZeebeCommonWorkerCommand extends RunnableWorkerZeroMQ {

    @Autowired
    private ZeebeCommonManager zeebeCommonManager;

    public ZeebeCommonWorkerCommand(String connection) {
        super(connection);
    }

    @Override
    public Object parse(String messageContent) {
        return this.mapper.fromJson(messageContent, ZeebeCommand.class);    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {
        System.out.println("ID " + this.identity);
        System.out.println("SENDER " + messageCommandZeroMQ.getSender());
        System.out.println("RECEIVER " + messageCommandZeroMQ.getReceiver());
        System.out.println("TYPE_COMMAND " + messageCommandZeroMQ.getTypeCommand());
        System.out.println("COMMAND " + messageCommandZeroMQ.getCommand());

        switch(messageCommandZeroMQ.getTypeCommand().toString()) {
            case COMMAND_DEPLOY:
                this.zeebeCommonManager.deployWorkflow("");
                break;
            case COMMAND_INSTANCIATE:
                this.zeebeCommonManager.instanciateWorkflow("", "");
                break;
            case COMMAND_SEND:
                this.zeebeCommonManager.sendMessage("");
                break;
            default:
                //DO NOTHING
                break;
        }
    }
}
