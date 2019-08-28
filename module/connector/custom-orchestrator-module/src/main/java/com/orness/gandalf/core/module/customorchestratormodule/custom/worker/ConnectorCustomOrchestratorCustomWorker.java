package com.orness.gandalf.core.module.customorchestratormodule.custom.worker;

import com.orness.gandalf.core.module.customorchestratormodule.core.properties.CustomOrchestratorProperties;
import com.orness.gandalf.core.module.customorchestratormodule.custom.manager.ConnectorCustomOrchestratorCustomManager;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.routing.RunnableRoutingRoutingWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "specificWorkerCommand")
@Profile(value = "custom-orchestrator-module")
public class ConnectorCustomOrchestratorCustomWorker extends RunnableRoutingRoutingWorkerZeroMQ {

    private ConnectorCustomOrchestratorCustomManager connectorCustomOrchestratorCustomManager;
    private CustomOrchestratorProperties customOrchestratorProperties;

    @Autowired
    public ConnectorCustomOrchestratorCustomWorker(ConnectorCustomOrchestratorCustomManager connectorCustomOrchestratorCustomManager, CustomOrchestratorProperties customOrchestratorProperties) {
        super();
        this.connectorCustomOrchestratorCustomManager = connectorCustomOrchestratorCustomManager;
        this.customOrchestratorProperties = customOrchestratorProperties;
        this.connect(this.customOrchestratorProperties.getWorker());
    }

    @Override
    public Object parse(String messageContent) {
        return null;
    }

    //TODO REVOIR ARGS

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {
        switch (messageCommandZeroMQ.getCommand().toString()) {
            case "untarProject":
                this.connectorCustomOrchestratorCustomManager.untarProject("", "");
                break;
            case "downloadProject":
                this.connectorCustomOrchestratorCustomManager.downloadProject("", "");
                break;
            case "downloadConfiguration":
                this.connectorCustomOrchestratorCustomManager.downloadConfiguration("", "");
                break;
            default:
                break;

        }
    }
}
