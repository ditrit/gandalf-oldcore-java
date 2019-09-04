package com.orness.gandalf.core.module.customorchestratormodule.normative.worker;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import com.orness.gandalf.core.module.customorchestratormodule.normative.manager.ConnectorCustomOrchestratorNormativeManager;
import com.orness.gandalf.core.module.customorchestratormodule.properties.ConnectorCustomOrchestratorProperties;
import com.orness.gandalf.core.module.zeromqcore.command.domain.MessageCommand;
import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

@Component(value = "normativeWorker")
@Profile(value = "custom-orchestrator")
public class ConnectorCustomOrchestratorNormativeWorker extends RunnableWorkerZeroMQ {

    private ConnectorCustomOrchestratorNormativeManager connectorCustomOrchestratorNormativeManager;
    private ConnectorProperties connectorProperties;
    private ConnectorCustomOrchestratorProperties connectorCustomOrchestratorProperties;
    private MessageCommand messageCommand;

    @Autowired
    public ConnectorCustomOrchestratorNormativeWorker(ConnectorProperties connectorProperties, ConnectorCustomOrchestratorProperties connectorCustomOrchestratorProperties, ConnectorCustomOrchestratorNormativeManager connectorCustomOrchestratorNormativeManager) {
        super();
        this.connectorCustomOrchestratorNormativeManager = connectorCustomOrchestratorNormativeManager;
        this.connectorProperties = connectorProperties;
        this.connectorCustomOrchestratorProperties = connectorCustomOrchestratorProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorProperties.getConnectorCommandBackEndConnection(), this.connectorProperties.getConnectorEventBackEndConnection(), this.connectorCustomOrchestratorProperties.getTopics());
    }

    //TODO PAYLOAD
    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        this.messageCommand = new MessageCommand(command);
        switch (messageCommand.getCommand()) {
            case "REGISTER":
                this.connectorCustomOrchestratorNormativeManager.register(messageCommand.getPayload());
                break;
            case "UNREGISTER":
                this.connectorCustomOrchestratorNormativeManager.unregister(messageCommand.getPayload());
                break;
            case "DEPLOY":
                this.connectorCustomOrchestratorNormativeManager.deploy(messageCommand.getPayload());
                break;
            case "UNDEPLOY":
                this.connectorCustomOrchestratorNormativeManager.undeploy(messageCommand.getPayload());
                break;
            case "START":
                this.connectorCustomOrchestratorNormativeManager.start(messageCommand.getPayload());
                break;
            case "STOP":
                this.connectorCustomOrchestratorNormativeManager.stop(messageCommand.getPayload());
                break;
            case "SCALE_UP":
                this.connectorCustomOrchestratorNormativeManager.scaleUp(messageCommand.getPayload());
                break;
            case "SCALE_DOWN":
                this.connectorCustomOrchestratorNormativeManager.scaleDown(messageCommand.getPayload());
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

