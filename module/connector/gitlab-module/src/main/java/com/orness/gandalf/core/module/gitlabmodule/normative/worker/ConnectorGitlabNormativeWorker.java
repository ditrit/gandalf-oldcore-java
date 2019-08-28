package com.orness.gandalf.core.module.gitlabmodule.normative.worker;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import com.orness.gandalf.core.module.connectorcore.properties.ConnectorRoutingProperties;
import com.orness.gandalf.core.module.gitlabmodule.normative.manager.ConnectorGitlabNormativeManager;
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

    @Autowired
    public ConnectorGitlabNormativeWorker(ConnectorProperties connectorProperties, ConnectorRoutingProperties connectorRoutingProperties, ConnectorGitlabNormativeManager connectorGitlabNormativeManager) {
        super();
        this.connectorGitlabNormativeManager = connectorGitlabNormativeManager;
        this.connectorProperties = connectorProperties;
        this.connectorRoutingProperties = connectorRoutingProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorRoutingProperties.getRoutingWorkerBackEndConnection(), this.connectorRoutingProperties.getRoutingSubscriberBackEndConnection(), this.connectorProperties.getTopics());
    }

    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
/*        switch(messageCommandZeroMQ.getTypeCommand().toString()) {
            case COMMAND_CLONE:
                this.gitlabCommonManager.cloneProject("");
                break;
            case COMMAND_PULL:
                this.gitlabCommonManager.pull("", "");
                break;
            case COMMAND_ADD:
                this.gitlabCommonManager.add("");
                break;
            case COMMAND_COMMIT:
                this.gitlabCommonManager.commit("");
                break;
            case COMMAND_PUSH:
                this.gitlabCommonManager.push("", "");
                break;
            case COMMAND_MERGE:
                this.gitlabCommonManager.merge("", "");
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
