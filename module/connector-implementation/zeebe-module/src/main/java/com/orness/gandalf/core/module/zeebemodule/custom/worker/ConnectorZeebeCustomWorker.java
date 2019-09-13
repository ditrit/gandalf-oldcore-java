package com.orness.gandalf.core.module.zeebemodule.custom.worker;

import com.orness.gandalf.core.module.zeebemodule.properties.ConnectorZeebeProperties;
import com.orness.gandalf.core.module.zeromqcore.constant.Constant;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "customWorker")
@ConditionalOnBean(ConnectorZeebeProperties.class)
public class ConnectorZeebeCustomWorker extends RunnableWorkerZeroMQ {

    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        return null;
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {

    }
}
