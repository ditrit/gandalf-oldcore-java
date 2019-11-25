package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.standard.worker;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.standard.manager.ConnectorNexusStandardManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.properties.ConnectorNexusProperties;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.command.domain.MessageCommand;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.ditrit.gandalf.gandalfjava.core.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_STANDARD;

@Component(value = "standardWorker")
@ConditionalOnBean(ConnectorNexusProperties.class)
public class ConnectorNexusStandardWorker extends RunnableWorkerZeroMQ {

    private ConnectorNexusStandardManager connectorNexusStandardManager;
    private ConnectorNexusProperties connectorNexusProperties;
    private MessageCommand messageCommand;

    @Autowired
    public ConnectorNexusStandardWorker(ConnectorNexusProperties connectorNexusProperties, ConnectorNexusStandardManager connectorNexusStandardManager) {
        super();
        this.connectorNexusStandardManager = connectorNexusStandardManager;
        this.connectorNexusProperties = connectorNexusProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_STANDARD, this.connectorNexusProperties.getConnectorCommandBackEndReceiveConnection(), this.connectorNexusProperties.getConnectorEventBackEndReceiveConnection(), null);
    }

    //TODO PAYLOAD
    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        this.messageCommand = new MessageCommand(command);
        switch(messageCommand.getCommand().toString()) {
            case "LIST_REPOSITORIES":
                this.connectorNexusStandardManager.listRepositories();
                break;
            case "LIST_ARTIFACTS":
                this.connectorNexusStandardManager.listArtifacts();
                break;
            case "DOWNLOAD_ARTIFACT":
                this.connectorNexusStandardManager.downloadArtifact(this.messageCommand.getPayload());
                break;
            case "UPLOAD_ARTIFACT":
                this.connectorNexusStandardManager.uploadArtifact(this.messageCommand.getPayload());
                break;
            case "DELETE_ARTIFACT":
                this.connectorNexusStandardManager.deleteArtifact(this.messageCommand.getPayload());
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
