package com.orness.gandalf.core.module.customorchestratormodule.normative.worker;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import com.orness.gandalf.core.module.connectorcore.properties.ConnectorRoutingProperties;
import com.orness.gandalf.core.module.customorchestratormodule.normative.manager.ConnectorCustomOrchestratorNormativeManager;
import com.orness.gandalf.core.module.zeromqcore.command.domain.MessageCommand;
import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

@Component(value = "commonWorkerCommand")
@Profile(value = "custom-orchestrator-module")
public class ConnectorCustomOrchestratorNormativeWorker extends RunnableWorkerZeroMQ {

    private ConnectorCustomOrchestratorNormativeManager connectorCustomOrchestratorNormativeManager;
    private ConnectorProperties connectorProperties;
    private ConnectorRoutingProperties connectorRoutingProperties;
    private MessageCommand messageCommand;

    @Autowired
    public ConnectorCustomOrchestratorNormativeWorker(ConnectorProperties connectorProperties, ConnectorRoutingProperties connectorRoutingProperties, ConnectorCustomOrchestratorNormativeManager connectorCustomOrchestratorNormativeManager) {
        super();
        this.connectorCustomOrchestratorNormativeManager = connectorCustomOrchestratorNormativeManager;
        this.connectorProperties = connectorProperties;
        this.connectorRoutingProperties = connectorRoutingProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorRoutingProperties.getRoutingWorkerBackEndConnection(), this.connectorRoutingProperties.getRoutingSubscriberBackEndConnection(), this.connectorProperties.getTopics());
    }

    //TODO PAYLOAD
    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        this.messageCommand = new MessageCommand(command);
        switch (messageCommand.getCommand()) {
            case "REGISTER":
                this.connectorCustomOrchestratorNormativeManager.register("", "");
                break;
            case "UNREGISTER":
                this.connectorCustomOrchestratorNormativeManager.unregister("", "");
                break;
            case "DEPLOY":
                this.connectorCustomOrchestratorNormativeManager.deploy("");
                break;
            case "UNDEPLOY":
                this.connectorCustomOrchestratorNormativeManager.undeploy("");
                break;
            case "START":
                this.connectorCustomOrchestratorNormativeManager.start("");
                break;
            case "STOP":
                this.connectorCustomOrchestratorNormativeManager.stop("");
                break;
            case "SCALE_UP":
                this.connectorCustomOrchestratorNormativeManager.scaleUp("");
                break;
            case "SCALE_DOWN":
                this.connectorCustomOrchestratorNormativeManager.scaleDown("");
                break;
            default:
                break;
        }
        return null;
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {

    }
}

