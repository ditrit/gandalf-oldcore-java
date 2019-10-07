package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.normative.worker;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.normative.manager.ConnectorGitlabNormativeManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.properties.ConnectorGitlabProperties;
import com.ditrit.gandalf.core.zeromqcore.command.domain.MessageCommand;
import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.event.domain.MessageEvent;
import com.ditrit.gandalf.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.ditrit.gandalf.core.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

@Component(value = "normativeWorker")
@ConditionalOnBean(ConnectorGitlabProperties.class)
public class ConnectorGitlabNormativeWorker extends RunnableWorkerZeroMQ {


    private ConnectorGitlabNormativeManager connectorGitlabNormativeManager;
    private ConnectorGitlabProperties connectorGitlabProperties;
    private MessageCommand messageCommand;
    private MessageEvent messageEvent;

    @Autowired
    public ConnectorGitlabNormativeWorker(ConnectorGitlabProperties connectorGitlabProperties, ConnectorGitlabNormativeManager connectorGitlabNormativeManager) {
        super();
        this.connectorGitlabNormativeManager = connectorGitlabNormativeManager;
        this.connectorGitlabProperties = connectorGitlabProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorGitlabProperties.getConnectorCommandBackEndConnection(), this.connectorGitlabProperties.getConnectorEventBackEndConnection(), null);
    }
//TODO PAYLOAD
    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        this.messageCommand = new MessageCommand(command);
        switch(messageCommand.getCommand()) {
            case "CLONE":
                this.connectorGitlabNormativeManager.cloneProject(messageCommand.getPayload());
                break;
            case "PULL":
                this.connectorGitlabNormativeManager.pull(messageCommand.getPayload());
                break;
            case "ADD":
                this.connectorGitlabNormativeManager.add(messageCommand.getPayload());
                break;
            case "COMMIT":
                this.connectorGitlabNormativeManager.commit(messageCommand.getPayload());
                break;
            case "PUSH":
                this.connectorGitlabNormativeManager.push(messageCommand.getPayload());
                break;
            case "MERGE":
                this.connectorGitlabNormativeManager.merge(messageCommand.getPayload());
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
