package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.standard.worker;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.standard.manager.ConnectorGitlabStandardManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.properties.ConnectorGitlabProperties;
import com.ditrit.gandalf.core.zeromqcore.command.domain.MessageCommand;
import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.event.domain.MessageEvent;
import com.ditrit.gandalf.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.ditrit.gandalf.core.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_STANDARD;

@Component(value = "standardWorker")
@ConditionalOnBean(ConnectorGitlabProperties.class)
public class ConnectorGitlabStandardWorker extends RunnableWorkerZeroMQ {


    private ConnectorGitlabStandardManager connectorGitlabStandardManager;
    private ConnectorGitlabProperties connectorGitlabProperties;
    private MessageCommand messageCommand;
    private MessageEvent messageEvent;

    @Autowired
    public ConnectorGitlabStandardWorker(ConnectorGitlabProperties connectorGitlabProperties, ConnectorGitlabStandardManager connectorGitlabStandardManager) {
        super();
        this.connectorGitlabStandardManager = connectorGitlabStandardManager;
        this.connectorGitlabProperties = connectorGitlabProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_STANDARD, this.connectorGitlabProperties.getConnectorCommandBackEndReceiveConnection(), this.connectorGitlabProperties.getConnectorEventBackEndReceiveConnection(), null);
    }

    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        this.messageCommand = new MessageCommand(command);
        switch(messageCommand.getCommand()) {
            case "CLONE":
                this.connectorGitlabStandardManager.cloneProject(messageCommand.getPayload());
                break;
            case "PULL":
                this.connectorGitlabStandardManager.pull(messageCommand.getPayload());
                break;
            case "ADD":
                this.connectorGitlabStandardManager.add(messageCommand.getPayload());
                break;
            case "COMMIT":
                this.connectorGitlabStandardManager.commit(messageCommand.getPayload());
                break;
            case "PUSH":
                this.connectorGitlabStandardManager.push(messageCommand.getPayload());
                break;
            case "MERGE":
                this.connectorGitlabStandardManager.merge(messageCommand.getPayload());
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
