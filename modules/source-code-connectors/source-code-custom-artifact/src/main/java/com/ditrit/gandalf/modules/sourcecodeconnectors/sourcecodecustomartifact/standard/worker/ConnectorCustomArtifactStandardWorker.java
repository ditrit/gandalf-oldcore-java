package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.standard.worker;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.standard.manager.ConnectorCustomArtifactStandardManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.properties.ConnectorCustomArtifactProperties;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.command.domain.MessageCommand;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.ditrit.gandalf.gandalfjava.core.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_STANDARD;

@Component(value = "standardWorker")
@ConditionalOnBean(ConnectorCustomArtifactProperties.class)
public class ConnectorCustomArtifactStandardWorker extends RunnableWorkerZeroMQ {

    private ConnectorCustomArtifactStandardManager connectorCustomArtifactStandardManager;
    private ConnectorCustomArtifactProperties connectorCustomArtifactProperties;
    private MessageCommand messageCommand;

    @Autowired
    public ConnectorCustomArtifactStandardWorker(ConnectorCustomArtifactProperties connectorCustomArtifactProperties, ConnectorCustomArtifactStandardManager connectorCustomArtifactStandardManager) {
        super();
        this.connectorCustomArtifactProperties = connectorCustomArtifactProperties;
        this.connectorCustomArtifactStandardManager = connectorCustomArtifactStandardManager;
        this.initRunnable(WORKER_SERVICE_CLASS_STANDARD, this.connectorCustomArtifactProperties.getConnectorCommandBackEndReceiveConnection(), this.connectorCustomArtifactProperties.getConnectorEventBackEndReceiveConnection(), null);
    }

    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        this.messageCommand = new MessageCommand(command);
        switch(messageCommand.getCommand()) {
            case "UPLOAD":
                this.connectorCustomArtifactStandardManager.uploadArtifact(messageCommand.getPayload());
                break;
            case "DOWNLOAD":
                this.connectorCustomArtifactStandardManager.downloadArtifact(messageCommand.getPayload());
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
