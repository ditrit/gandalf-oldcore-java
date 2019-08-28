package com.orness.gandalf.core.module.zeebemodule.normative.worker;

import com.orness.gandalf.core.module.zeebemodule.normative.manager.ZeebeCommonManager;
import com.orness.gandalf.core.module.zeebemodule.core.ZeebeCommand;
import com.orness.gandalf.core.module.zeebemodule.core.properties.ZeebeProperties;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.routing.RunnableRoutingRoutingWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.orness.gandalf.core.module.workflowenginemodule.constant.WorkflowEngineConstant.*;

@Component(value = "commonWorkerCommand")
@Profile(value = "zeebe-module")
public class ZeebeCommonRoutingRoutingWorkerCommand extends RunnableRoutingRoutingWorkerZeroMQ {

    private ZeebeCommonManager zeebeCommonManager;
    private ZeebeProperties zeebeProperties;

    @Autowired
    public ZeebeCommonRoutingRoutingWorkerCommand(ZeebeCommonManager zeebeCommonManager, ZeebeProperties zeebeProperties) {
        super();
        this.zeebeCommonManager = zeebeCommonManager;
        this.zeebeProperties = zeebeProperties;
        this.connect(zeebeProperties.getWorker());
    }

    @Override
    public Object parse(String messageContent) {
        return this.mapper.fromJson(messageContent, ZeebeCommand.class);
    }

    //TODO REVOIR ARGS
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
