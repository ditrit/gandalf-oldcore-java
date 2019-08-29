package com.orness.gandalf.core.module.nexusmodule.normative.worker;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import com.orness.gandalf.core.module.connectorcore.properties.ConnectorRoutingProperties;
import com.orness.gandalf.core.module.nexusmodule.normative.manager.ConnectorNexusNormativeManager;
import com.orness.gandalf.core.module.zeromqcore.command.domain.MessageCommand;
import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

@Component(value = "commonWorkerCommand")
@Profile(value = "nexus-module")
public class ConnectorNexusNormativeWorker extends RunnableWorkerZeroMQ {

    private ConnectorNexusNormativeManager connectorNexusNormativeManager;
    private ConnectorProperties connectorProperties;
    private ConnectorRoutingProperties connectorRoutingProperties;
    private MessageCommand messageCommand;

    @Autowired
    public ConnectorNexusNormativeWorker(ConnectorProperties connectorProperties, ConnectorRoutingProperties connectorRoutingProperties, ConnectorNexusNormativeManager connectorNexusNormativeManager) {
        super();
        this.connectorNexusNormativeManager = connectorNexusNormativeManager;
        this.connectorProperties = connectorProperties;
        this.connectorRoutingProperties = connectorRoutingProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorRoutingProperties.getRoutingWorkerBackEndConnection(), this.connectorRoutingProperties.getRoutingSubscriberBackEndConnection(), this.connectorProperties.getTopics());
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
                this.connectorNexusNormativeManager.downloadArtifact(0L);
                break;
            case "UPLOAD_ARTIFACT":
                this.connectorNexusNormativeManager.uploadArtifact("");
                break;
            case "DELETE_ARTIFACT":
                this.connectorNexusNormativeManager.deleteArtifact(0L);
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
