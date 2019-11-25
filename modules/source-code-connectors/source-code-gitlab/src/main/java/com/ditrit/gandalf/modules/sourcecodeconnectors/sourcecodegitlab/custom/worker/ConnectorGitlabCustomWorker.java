package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.custom.worker;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.properties.ConnectorGitlabProperties;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "customWorker")
@ConditionalOnBean(ConnectorGitlabProperties.class)
public class ConnectorGitlabCustomWorker extends RunnableWorkerZeroMQ {


    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        return null;
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {

    }
}
