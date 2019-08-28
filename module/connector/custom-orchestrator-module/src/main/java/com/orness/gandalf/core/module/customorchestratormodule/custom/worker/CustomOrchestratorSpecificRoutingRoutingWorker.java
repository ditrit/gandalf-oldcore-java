package com.orness.gandalf.core.module.customorchestratormodule.custom.worker;

import com.orness.gandalf.core.module.customorchestratormodule.core.properties.CustomOrchestratorProperties;
import com.orness.gandalf.core.module.customorchestratormodule.custom.manager.CustomOrchestratorSpecificManager;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.routing.RunnableRoutingRoutingWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "specificWorkerCommand")
@Profile(value = "custom-orchestrator-module")
public class CustomOrchestratorSpecificRoutingRoutingWorker extends RunnableRoutingRoutingWorkerZeroMQ {

    private CustomOrchestratorSpecificManager customOrchestratorSpecificManager;
    private CustomOrchestratorProperties customOrchestratorProperties;

    @Autowired
    public CustomOrchestratorSpecificRoutingRoutingWorker(CustomOrchestratorSpecificManager customOrchestratorSpecificManager, CustomOrchestratorProperties customOrchestratorProperties) {
        super();
        this.customOrchestratorSpecificManager = customOrchestratorSpecificManager;
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
                this.customOrchestratorSpecificManager.untarProject("", "");
                break;
            case "downloadProject":
                this.customOrchestratorSpecificManager.downloadProject("", "");
                break;
            case "downloadConfiguration":
                this.customOrchestratorSpecificManager.downloadConfiguration("", "");
                break;
            default:
                break;

        }
    }
}
