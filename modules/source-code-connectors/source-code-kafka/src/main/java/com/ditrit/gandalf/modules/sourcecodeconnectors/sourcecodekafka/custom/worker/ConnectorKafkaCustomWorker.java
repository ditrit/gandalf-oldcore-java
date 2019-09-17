package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.custom.worker;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.properties.ConnectorKafkaProperties;
import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.core.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component(value = "customWorker")
@ConditionalOnBean(ConnectorKafkaProperties.class)
public class ConnectorKafkaCustomWorker extends RunnableWorkerZeroMQ {

    @Override
    protected Constant.Result executeRoutingWorkerCommand(ZMsg command) {
        return null;
    }

    @Override
    protected void executeRoutingSubscriberCommand(ZMsg command) {

    }
}

