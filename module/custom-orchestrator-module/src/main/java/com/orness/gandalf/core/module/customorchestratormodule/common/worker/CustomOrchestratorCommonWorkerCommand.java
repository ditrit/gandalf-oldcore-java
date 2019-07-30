package com.orness.gandalf.core.module.customorchestratormodule.common.worker;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.customorchestratormodule.common.manager.CustomOrchestratorCommonManager;
import com.orness.gandalf.core.module.customorchestratormodule.core.CustomOrchestratorCommand;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

import static com.orness.gandalf.core.module.orchestratormodule.constant.OrchestratorConstant.*;

//TODO
public class CustomOrchestratorCommonWorkerCommand extends RunnableWorkerZeroMQ {

    @Autowired
    private CustomOrchestratorCommonManager customOrchestratorCommonManager;
    private Gson mapper;

    public CustomOrchestratorCommonWorkerCommand(String connection) {
        super(connection);
        mapper = new Gson();
    }

    @Override
    public Object parse(String messageContent) {
        return mapper.fromJson(messageContent, CustomOrchestratorCommand.class);
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

