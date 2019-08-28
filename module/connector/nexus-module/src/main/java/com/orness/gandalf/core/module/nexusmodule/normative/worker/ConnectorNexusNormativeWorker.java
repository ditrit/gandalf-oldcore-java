package com.orness.gandalf.core.module.nexusmodule.normative.worker;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import com.orness.gandalf.core.module.connectorcore.properties.ConnectorRoutingProperties;
import com.orness.gandalf.core.module.nexusmodule.normative.manager.ConnectorNexusNormativeManager;
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

    @Autowired
    public ConnectorNexusNormativeWorker(ConnectorProperties connectorProperties, ConnectorRoutingProperties connectorRoutingProperties, ConnectorNexusNormativeManager connectorNexusNormativeManager) {
        super();
        this.connectorNexusNormativeManager = connectorNexusNormativeManager;
        this.connectorProperties = connectorProperties;
        this.connectorRoutingProperties = connectorRoutingProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorRoutingProperties.getRoutingWorkerBackEndConnection(), this.connectorRoutingProperties.getRoutingSubscriberBackEndConnection(), this.connectorProperties.getTopics());
    }

    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
 /*       //TODO REVOIR ARGS
        switch(messageCommandZeroMQ.getTypeCommand().toString()) {
            case COMMAND_LIST_REPOSITORIES:
                this.nexusCommonManager.listRepositories();
                break;
            case COMMAND_LIST_ARTIFACTS:
                this.nexusCommonManager.listArtifacts();
                break;
            case COMMAND_DOWNLOAD_ARTIFACT:
                this.nexusCommonManager.downloadArtifact(0L);
                break;
            case COMMAND_UPLOAD_ARTIFACT:
                this.nexusCommonManager.uploadArtifact("");
                break;
            case COMMAND_DELETE_ARTIFACT:
                this.nexusCommonManager.deleteArtifact(0L);
                break;
            default:
                //DO NOTHING
                break;
        }*/
        return null;
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {

    }
}
