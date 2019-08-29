package com.orness.gandalf.core.module.zeebemodule.normative.worker;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import com.orness.gandalf.core.module.connectorcore.properties.ConnectorRoutingProperties;
import com.orness.gandalf.core.module.zeebemodule.normative.manager.ConnectorZeebeNormativeManager;
import com.orness.gandalf.core.module.zeromqcore.command.domain.MessageCommand;
import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

@Component(value = "commonWorkerCommand")
@Profile(value = "zeebe-module")
public class ConnectorZeebeNormativeWorker extends RunnableWorkerZeroMQ {

    private ConnectorZeebeNormativeManager connectorZeebeNormativeManager;
    private ConnectorProperties connectorProperties;
    private ConnectorRoutingProperties connectorRoutingProperties;
    private MessageCommand messageCommand;

    @Autowired
    public ConnectorZeebeNormativeWorker( ConnectorProperties connectorProperties, ConnectorRoutingProperties connectorRoutingProperties, ConnectorZeebeNormativeManager connectorZeebeNormativeManager) {
        super();
        this.connectorZeebeNormativeManager = connectorZeebeNormativeManager;
        this.connectorProperties = connectorProperties;
        this.connectorRoutingProperties = connectorRoutingProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorRoutingProperties.getRoutingWorkerBackEndConnection(), this.connectorRoutingProperties.getRoutingSubscriberBackEndConnection(), this.connectorProperties.getTopics());
    }
//TODO PAYLOAD
    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        this.messageCommand = new MessageCommand(command);
        switch(messageCommand.getCommand().toString()) {
            case "DEPLOY":
                this.connectorZeebeNormativeManager.deployWorkflow("");
                break;
            case "INSTANCIATE":
                this.connectorZeebeNormativeManager.instanciateWorkflow("", "");
                break;
            case "SEND":
                this.connectorZeebeNormativeManager.sendMessage("");
                break;
            default:
                //DO NOTHING
                break;
        }
        return null;
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {

    }
}
