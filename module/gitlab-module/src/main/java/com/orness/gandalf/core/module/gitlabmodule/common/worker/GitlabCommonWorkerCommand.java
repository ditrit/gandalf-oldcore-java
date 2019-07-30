package com.orness.gandalf.core.module.gitlabmodule.common.worker;

import com.google.gson.Gson;
import com.orness.gandalf.core.module.gitlabmodule.common.manager.GitlabCommonManager;
import com.orness.gandalf.core.module.gitlabmodule.core.GitlabCommand;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

import static com.orness.gandalf.core.module.versioncontrolmodule.constant.VersionControlConstant.*;

//TODO ARGS
public class GitlabCommonWorkerCommand extends RunnableWorkerZeroMQ {

    @Autowired
    private GitlabCommonManager gitlabCommonManager;
    private Gson mapper;

    public GitlabCommonWorkerCommand(String connection) {
        super(connection);
        mapper = new Gson();
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
