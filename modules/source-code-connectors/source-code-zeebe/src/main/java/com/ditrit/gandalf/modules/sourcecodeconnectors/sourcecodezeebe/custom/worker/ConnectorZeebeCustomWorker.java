package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.custom.worker;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.properties.ConnectorZeebeProperties;
import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "customWorker")
@ConditionalOnBean(ConnectorZeebeProperties.class)
public class ConnectorZeebeCustomWorker extends RunnableWorkerZeroMQ {

    @Override
    protected String executeWorkerCommandFunction(ZMsg commandExecute) {
        return null;
    }

    @Override
    protected void executeWorkerEventFunction(ZMsg commandExecute) {

    }

    @Override
    protected void sendReadyCommand() {

    }
}
