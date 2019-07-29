package com.orness.gandalf.core.module.customorchestratormodule.specific.worker;

import com.orness.gandalf.core.module.customorchestratormodule.specific.manager.CustomOrchestratorSpecificManager;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomOrchestratorSpecificWorker extends RunnableWorkerZeroMQ {

    @Autowired
    private CustomOrchestratorSpecificManager customOrchestratorSpecificManager;

    public CustomOrchestratorSpecificWorker(String connection) {
        super(connection);
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
