package com.orness.gandalf.core.module.gitlabmodule.normative.worker;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import com.orness.gandalf.core.module.connectorcore.properties.ConnectorRoutingProperties;
import com.orness.gandalf.core.module.gitlabmodule.normative.manager.ConnectorGitlabNormativeManager;
import com.orness.gandalf.core.module.zeromqcore.command.domain.MessageCommand;
import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

//TODO ARGS
public class ConnectorGitlabNormativeWorker extends RunnableWorkerZeroMQ {


    private ConnectorGitlabNormativeManager connectorGitlabNormativeManager;
    private ConnectorProperties connectorProperties;
    private ConnectorRoutingProperties connectorRoutingProperties;
    private MessageCommand messageCommand;

    @Autowired
    public ConnectorGitlabNormativeWorker(ConnectorProperties connectorProperties, ConnectorRoutingProperties connectorRoutingProperties, ConnectorGitlabNormativeManager connectorGitlabNormativeManager) {
        super();
        this.connectorGitlabNormativeManager = connectorGitlabNormativeManager;
        this.connectorProperties = connectorProperties;
        this.connectorRoutingProperties = connectorRoutingProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorRoutingProperties.getRoutingWorkerBackEndConnection(), this.connectorRoutingProperties.getRoutingSubscriberBackEndConnection(), this.connectorProperties.getTopics());
    }
//TODO PAYLOAD
    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        this.messageCommand = new MessageCommand(command);
        switch(messageCommand.getCommand()) {
            case "CLONE":
                this.connectorGitlabNormativeManager.cloneProject("", "");
                break;
            case "PULL":
                this.connectorGitlabNormativeManager.pull("", "");
                break;
            case "ADD":
                this.connectorGitlabNormativeManager.add("");
                break;
            case "COMMIT":
                this.connectorGitlabNormativeManager.commit("");
                break;
            case "PUSH":
                this.connectorGitlabNormativeManager.push("", "");
                break;
            case "MERGE":
                this.connectorGitlabNormativeManager.merge("", "");
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
