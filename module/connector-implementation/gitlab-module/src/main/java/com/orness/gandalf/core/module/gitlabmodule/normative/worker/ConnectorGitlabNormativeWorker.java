package com.orness.gandalf.core.module.gitlabmodule.normative.worker;

import com.orness.gandalf.core.module.gitlabmodule.normative.manager.ConnectorGitlabNormativeManager;
import com.orness.gandalf.core.module.gitlabmodule.properties.ConnectorGitlabProperties;
import com.orness.gandalf.core.module.zeromqcore.command.domain.MessageCommand;
import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.event.domain.MessageEvent;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

@Component(value = "normativeWorker")
@Profile(value = "gitlab")
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
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorGitlabProperties.getConnectorCommandBackEndConnection(), this.connectorGitlabProperties.getConnectorEventBackEndConnection(), this.connectorGitlabProperties.getTopics());
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
