package com.orness.gandalf.core.module.zeebemodule.normative.worker;

import com.orness.gandalf.core.module.zeebemodule.normative.manager.ConnectorZeebeNormativeManager;
import com.orness.gandalf.core.module.zeebemodule.properties.ConnectorZeebeProperties;
import com.orness.gandalf.core.module.zeromqcore.command.domain.MessageCommand;
import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.event.domain.MessageEvent;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

@Component(value = "normativeWorker")
@Profile(value = "zeebe")
public class ConnectorZeebeNormativeWorker extends RunnableWorkerZeroMQ {

    private ConnectorZeebeNormativeManager connectorZeebeNormativeManager;
    private ConnectorZeebeProperties connectorZeebeProperties;
    private MessageCommand messageCommand;
    private MessageEvent messageEvent;

    @Autowired
    public ConnectorZeebeNormativeWorker(ConnectorZeebeProperties connectorZeebeProperties, ConnectorZeebeNormativeManager connectorZeebeNormativeManager) {
        super();
        this.connectorZeebeNormativeManager = connectorZeebeNormativeManager;
        this.connectorZeebeProperties = connectorZeebeProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorZeebeProperties.getConnectorCommandBackEndConnection(), this.connectorZeebeProperties.getConnectorEventBackEndConnection(), this.connectorZeebeProperties.getTopics());
    }
//TODO PAYLOAD
    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        this.messageCommand = new MessageCommand(command);
        switch(messageCommand.getCommand()) {
            case "DEPLOY":
                this.connectorZeebeNormativeManager.deployWorkflow(this.messageCommand.getPayload());
                break;
            case "INSTANCIATE":
                this.connectorZeebeNormativeManager.instanciateWorkflow(this.messageCommand.getPayload());
                break;
            case "SEND":
                this.connectorZeebeNormativeManager.sendMessage(this.messageCommand.getPayload());
                break;
            default:
                //DO NOTHING
                break;
        }
        return null;
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {
        this.messageEvent = new MessageEvent(command);
        System.out.println(messageEvent.getEvent());
        switch(messageEvent.getEvent()) {
            case "HOOK_MERGE":
                this.connectorZeebeNormativeManager.hookMerge(this.messageEvent);
                break;
            default:
                //DO NOTHING
                break;
        }
    }
}
