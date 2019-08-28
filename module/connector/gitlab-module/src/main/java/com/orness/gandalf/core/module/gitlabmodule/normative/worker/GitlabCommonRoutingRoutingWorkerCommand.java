package com.orness.gandalf.core.module.gitlabmodule.normative.worker;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.gitlabmodule.normative.manager.GitlabNormativeManagerConnector;
import com.orness.gandalf.core.module.gitlabmodule.core.GitlabCommand;
import com.orness.gandalf.core.module.gitlabmodule.core.properties.GitlabProperties;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.routing.RunnableRoutingRoutingWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

import static com.orness.gandalf.core.module.versioncontrolmodule.constant.ConnectorVersionControlConstant.*;

//TODO ARGS
public class GitlabCommonRoutingRoutingWorkerCommand extends RunnableRoutingRoutingWorkerZeroMQ {


    private GitlabNormativeManagerConnector gitlabCommonManager;
    private GitlabProperties gitlabProperties;
    private Gson mapper;

    @Autowired
    public GitlabCommonRoutingRoutingWorkerCommand(GitlabNormativeManagerConnector gitlabCommonManager, GitlabProperties gitlabProperties) {
        super();
        this.gitlabCommonManager = gitlabCommonManager;
        this.gitlabProperties = gitlabProperties;
        this.mapper = new Gson();
        this.connect(gitlabProperties.getWorker());
    }

    @Override
    public Object parse(String messageContent) {
        return mapper.fromJson(messageContent, GitlabCommand.class);
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {
        System.out.println("ID " + this.identity);
        System.out.println("SENDER " + messageCommandZeroMQ.getSender());
        System.out.println("RECEIVER " + messageCommandZeroMQ.getReceiver());
        System.out.println("TYPE_COMMAND " + messageCommandZeroMQ.getTypeCommand());
        System.out.println("COMMAND " + messageCommandZeroMQ.getCommand());

        switch(messageCommandZeroMQ.getTypeCommand().toString()) {
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
        }
    }
}
