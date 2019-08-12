package com.orness.gandalf.core.module.customorchestratormodule.common.worker;

import com.orness.gandalf.core.module.customorchestratormodule.common.manager.CustomOrchestratorCommonManager;
import com.orness.gandalf.core.module.customorchestratormodule.core.CustomOrchestratorCommand;
import com.orness.gandalf.core.module.customorchestratormodule.core.properties.CustomOrchestratorProperties;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.orness.gandalf.core.module.orchestratormodule.constant.OrchestratorConstant.*;

@Component(value = "commonWorkerCommand")
@Profile(value = "custom-orchestrator-module")
public class CustomOrchestratorCommonWorkerCommand extends RunnableWorkerZeroMQ {

    private CustomOrchestratorCommonManager customOrchestratorCommonManager;
    private CustomOrchestratorProperties customOrchestratorProperties;

    @Autowired
    public CustomOrchestratorCommonWorkerCommand(CustomOrchestratorCommonManager customOrchestratorCommonManager, CustomOrchestratorProperties customOrchestratorProperties) {
        super();
        this.customOrchestratorCommonManager = customOrchestratorCommonManager;
        this.customOrchestratorProperties = customOrchestratorProperties;
        this.connect(this.customOrchestratorProperties.getWorker());
    }

    @Override
    public Object parse(String messageContent) {
        return this.mapper.fromJson(messageContent, CustomOrchestratorCommand.class);
    }

    //TODO ARGS

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {
        switch (messageCommandZeroMQ.getCommand().toString()) {
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

        }
    }
}
