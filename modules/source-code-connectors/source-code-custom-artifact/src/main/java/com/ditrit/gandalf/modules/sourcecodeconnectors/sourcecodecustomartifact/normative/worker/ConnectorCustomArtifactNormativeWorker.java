package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.normative.worker;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.normative.manager.ConnectorCustomArtifactNormativeManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.properties.ConnectorCustomArtifactProperties;
import com.ditrit.gandalf.core.zeromqcore.command.domain.MessageCommand;
import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.ditrit.gandalf.core.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

@Component(value = "normativeWorker")
@ConditionalOnBean(ConnectorCustomArtifactProperties.class)
public class ConnectorCustomArtifactNormativeWorker extends RunnableWorkerZeroMQ {

    private ConnectorCustomArtifactNormativeManager connectorCustomArtifactNormativeManager;
    private ConnectorCustomArtifactProperties connectorCustomArtifactProperties;
    private MessageCommand messageCommand;

    @Autowired
    public ConnectorCustomArtifactNormativeWorker(ConnectorCustomArtifactProperties connectorCustomArtifactProperties, ConnectorCustomArtifactNormativeManager connectorCustomArtifactNormativeManager) {
        super();
        this.connectorCustomArtifactProperties = connectorCustomArtifactProperties;
        this.connectorCustomArtifactNormativeManager = connectorCustomArtifactNormativeManager;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorCustomArtifactProperties.getConnectorCommandBackEndConnection(), this.connectorCustomArtifactProperties.getConnectorEventBackEndConnection(), this.connectorCustomArtifactProperties.getTopics());
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
