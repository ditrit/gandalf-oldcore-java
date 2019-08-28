package com.orness.gandalf.core.module.customorchestratormodule.normative.worker;

import com.orness.gandalf.core.module.connectorcore.properties.ConnectorProperties;
import com.orness.gandalf.core.module.connectorcore.properties.ConnectorRoutingProperties;
import com.orness.gandalf.core.module.customorchestratormodule.normative.manager.ConnectorCustomOrchestratorNormativeManager;
import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.module.connectorcore.constant.ConnectorConstant.WORKER_SERVICE_CLASS_NORMATIVE;

@Component(value = "commonWorkerCommand")
@Profile(value = "custom-orchestrator-module")
public class ConnectorCustomOrchestratorNormativeWorker extends RunnableWorkerZeroMQ {

    private ConnectorCustomOrchestratorNormativeManager connectorCustomOrchestratorNormativeManager;
    private ConnectorProperties connectorProperties;
    private ConnectorRoutingProperties connectorRoutingProperties;

    @Autowired
    public ConnectorCustomOrchestratorNormativeWorker(ConnectorProperties connectorProperties, ConnectorRoutingProperties connectorRoutingProperties, ConnectorCustomOrchestratorNormativeManager connectorCustomOrchestratorNormativeManager) {
        super();
        this.connectorCustomOrchestratorNormativeManager = connectorCustomOrchestratorNormativeManager;
        this.connectorProperties = connectorProperties;
        this.connectorRoutingProperties = connectorRoutingProperties;
        this.initRunnable(WORKER_SERVICE_CLASS_NORMATIVE, this.connectorRoutingProperties.getRoutingWorkerBackEndConnection(), this.connectorRoutingProperties.getRoutingSubscriberBackEndConnection(), this.connectorProperties.getTopics());
    }

    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
/*        switch (messageCommandZeroMQ.getCommand().toString()) {
            case COMMAND_REGISTER:
                this.customOrchestratorCommonManager.register("", "");
                break;
            case COMMAND_UNREGISTER:
                this.customOrchestratorCommonManager.unregister("", "");
                break;
            case COMMAND_DEPLOY:
                this.customOrchestratorCommonManager.deploy("");
                break;
            case COMMAND_UNDEPLOY:
                this.customOrchestratorCommonManager.undeploy("");
                break;
            case COMMAND_START:
                this.customOrchestratorCommonManager.start("");
                break;
            case COMMAND_STOP:
                this.customOrchestratorCommonManager.stop("");
                break;
            case COMMAND_SCALE_UP:
                this.customOrchestratorCommonManager.scaleUp("");
                break;
            case COMMAND_SCALE_DOWN:
                this.customOrchestratorCommonManager.scaleDown("");
                break;
            default:
                break;
        }*/
        return null;
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {

    }
}

