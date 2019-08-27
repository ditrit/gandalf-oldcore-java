package com.orness.gandalf.core.module.gitlabmodule.specific.worker;

import com.orness.gandalf.core.module.gitlabmodule.core.properties.GitlabProperties;
import com.orness.gandalf.core.module.gitlabmodule.specific.manager.GitlabSpecificManager;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.routing.RunnableRoutingRoutingWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

//TODO
public class GitlabSpecificRoutingRoutingWorkerCommand extends RunnableRoutingRoutingWorkerZeroMQ {

    private GitlabSpecificManager gitlabSpecificManager;
    private GitlabProperties gitlabProperties;

    @Autowired
    public GitlabSpecificRoutingRoutingWorkerCommand(GitlabSpecificManager gitlabSpecificManager, GitlabProperties gitlabProperties) {
        super();
        this.gitlabSpecificManager = gitlabSpecificManager;
        this.gitlabProperties = gitlabProperties;
        this.connect(gitlabProperties.getWorker());
    }

    @Override
    public Object parse(String messageContent) {
        return null;
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

    }
}
