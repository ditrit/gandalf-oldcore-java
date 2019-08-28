package com.orness.gandalf.core.module.kafkamodule.custom.worker;

import com.orness.gandalf.core.module.kafkamodule.core.properties.KafkaProperties;
import com.orness.gandalf.core.module.kafkamodule.custom.manager.KafkaSpecificManager;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.routing.RunnableRoutingRoutingWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "specificWorkerCommand")
@Profile(value = "kafka-module")
public class KafkaSpecificRoutingRoutingWorkerCommand extends RunnableRoutingRoutingWorkerZeroMQ {

    private KafkaSpecificManager kafkaSpecificManager;
    private KafkaProperties kafkaProperties;

    @Autowired
    public KafkaSpecificRoutingRoutingWorkerCommand(KafkaSpecificManager kafkaSpecificManager, KafkaProperties kafkaProperties) {
        super();
        this.kafkaSpecificManager = kafkaSpecificManager;
        this.kafkaProperties = kafkaProperties;
        this.connect(kafkaProperties.getWorker());
    }

    @Override
    public Object parse(String messageContent) {
        return null;
    }

    //TODO
    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

    }
}
