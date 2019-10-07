package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.normative.worker;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.normative.manager.ConnectorNexusNormativeManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.properties.ConnectorNexusProperties;
import com.ditrit.gandalf.core.zeromqcore.command.domain.MessageCommand;
import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.ditrit.gandalf.core.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

@Component(value = "normativeWorker")
@ConditionalOnBean(ConnectorNexusProperties.class)
public class ConnectorNexusNormativeWorker extends RunnableWorkerZeroMQ {

    private ConnectorNexusNormativeManager connectorNexusNormativeManager;
    private ConnectorNexusProperties connectorNexusProperties;
    private MessageCommand messageCommand;

    @Autowired
    public ConnectorNexusNormativeWorker(ConnectorNexusProperties connectorNexusProperties, ConnectorNexusNormativeManager connectorNexusNormativeManager) {
        super();
        this.connectorNexusNormativeManager = connectorNexusNormativeManager;
        this.connectorNexusProperties = connectorNexusProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorNexusProperties.getConnectorCommandBackEndConnection(), this.connectorNexusProperties.getConnectorEventBackEndConnection(), null);
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
