package com.orness.gandalf.core.module.nexusmodule.normative.worker;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import com.orness.gandalf.core.module.nexusmodule.normative.manager.ConnectorNexusNormativeManager;
import com.orness.gandalf.core.module.nexusmodule.properties.ConnectorNexusProperties;
import com.orness.gandalf.core.module.zeromqcore.command.domain.MessageCommand;
import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

@Component(value = "normativeWorker")
@Profile(value = "nexus")
public class ConnectorNexusNormativeWorker extends RunnableWorkerZeroMQ {

    private ConnectorNexusNormativeManager connectorNexusNormativeManager;
    private ConnectorProperties connectorProperties;
    private ConnectorNexusProperties connectorNexusProperties;
    private MessageCommand messageCommand;

    @Autowired
    public ConnectorNexusNormativeWorker(ConnectorProperties connectorProperties, ConnectorNexusProperties connectorNexusProperties, ConnectorNexusNormativeManager connectorNexusNormativeManager) {
        super();
        this.connectorNexusNormativeManager = connectorNexusNormativeManager;
        this.connectorProperties = connectorProperties;
        this.connectorNexusProperties = connectorNexusProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorProperties.getRoutingWorkerBackEndConnection(), this.connectorProperties.getRoutingSubscriberBackEndConnection(), this.connectorNexusProperties.getTopics());
    }

    //TODO PAYLOAD
    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        this.messageCommand = new MessageCommand(command);
        switch(messageCommand.getCommand().toString()) {
            case "LIST_REPOSITORIES":
                this.connectorNexusNormativeManager.listRepositories();
                break;
            case "LIST_ARTIFACTS":
                this.connectorNexusNormativeManager.listArtifacts();
                break;
            case "DOWNLOAD_ARTIFACT":
                this.connectorNexusNormativeManager.downloadArtifact(this.messageCommand.getPayload());
                break;
            case "UPLOAD_ARTIFACT":
                this.connectorNexusNormativeManager.uploadArtifact(this.messageCommand.getPayload());
                break;
            case "DELETE_ARTIFACT":
                this.connectorNexusNormativeManager.deleteArtifact(this.messageCommand.getPayload());
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
