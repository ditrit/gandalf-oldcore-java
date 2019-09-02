package com.orness.gandalf.core.module.customorchestratormodule.custom.worker;

import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "customWorker")
@Profile(value = "custom-orchestrator")
public class ConnectorCustomOrchestratorCustomWorker extends RunnableWorkerZeroMQ {

    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        return null;
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {

    }
}
