package com.orness.gandalf.core.module.customartifactmodule.normative.worker;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import com.orness.gandalf.core.module.connectorcore.properties.ConnectorRoutingProperties;
import com.orness.gandalf.core.module.customartifactmodule.normative.manager.ConnectorCustomArtifactNormativeManager;
import com.orness.gandalf.core.module.zeromqcore.command.domain.MessageCommand;
import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

@Component(value = "normativeWorker")
@Profile(value = "custom-artifact-module")
public class ConnectorCustomArtifactNormativeWorker extends RunnableWorkerZeroMQ {

    private ConnectorCustomArtifactNormativeManager connectorCustomArtifactNormativeManager;
    private ConnectorProperties connectorProperties;
    private ConnectorRoutingProperties connectorRoutingProperties;
    private MessageCommand messageCommand;

    @Autowired
    public ConnectorCustomArtifactNormativeWorker(ConnectorProperties connectorProperties, ConnectorRoutingProperties connectorRoutingProperties, ConnectorCustomArtifactNormativeManager connectorCustomArtifactNormativeManager) {
        super();
        this.connectorProperties = connectorProperties;
        this.connectorRoutingProperties = connectorRoutingProperties;
        this.connectorCustomArtifactNormativeManager = connectorCustomArtifactNormativeManager;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorRoutingProperties.getRoutingWorkerBackEndConnection(), this.connectorRoutingProperties.getRoutingSubscriberBackEndConnection(), this.connectorProperties.getTopics());
    }

    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        this.messageCommand = new MessageCommand(command);
        switch(messageCommand.getCommand()) {
            case "UPLOAD":
                this.connectorCustomArtifactNormativeManager.uploadArtifact(messageCommand.getPayload());
                break;
            case "DOWNLOAD":
                this.connectorCustomArtifactNormativeManager.downloadArtifact(messageCommand.getPayload());
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
