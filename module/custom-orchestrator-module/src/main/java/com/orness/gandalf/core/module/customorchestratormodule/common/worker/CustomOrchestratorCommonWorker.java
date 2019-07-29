package com.orness.gandalf.core.module.customorchestratormodule.common.worker;

import com.orness.gandalf.core.module.customorchestratormodule.common.manager.CustomOrchestratorCommonManager;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

//TODO
public class CustomOrchestratorCommonWorker extends RunnableWorkerZeroMQ {

    @Autowired
    private CustomOrchestratorCommonManager customOrchestratorCommonManager;

    public CustomOrchestratorCommonWorker(String connection) {
        super(connection);
    }

    //TODO ARGS

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {
        switch (messageCommandZeroMQ.getCommand().toString()) {
            case "register":
                this.customOrchestratorCommonManager.register("", "");
                break;
            case "unregister":
                this.customOrchestratorCommonManager.unregister("", "");
                break;
            case "deploy":
                this.customOrchestratorCommonManager.deploy("");
                break;
            case "undeploy":
                this.customOrchestratorCommonManager.undeploy("");
                break;
            case "start":
                this.customOrchestratorCommonManager.start("");
                break;
            case "stop":
                this.customOrchestratorCommonManager.stop("");
                break;
            case "scaleUp":
                this.customOrchestratorCommonManager.scaleUp("");
                break;
            case "scaleDown":
                this.customOrchestratorCommonManager.scaleDown("");
                break;
            default:
                break;

        }
    }
}

