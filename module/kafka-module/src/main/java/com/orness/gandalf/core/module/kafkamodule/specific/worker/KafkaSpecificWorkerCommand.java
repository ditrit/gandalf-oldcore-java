package com.orness.gandalf.core.module.kafkamodule.specific.worker;

import com.orness.gandalf.core.module.kafkamodule.core.properties.KafkaProperties;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "specificWorkerCommand")
@Profile(value = "kafka-module")
public class KafkaSpecificWorkerCommand extends RunnableWorkerZeroMQ {

    private KafkaProperties kafkaProperties;

    @Autowired
    public KafkaSpecificWorkerCommand(KafkaProperties kafkaProperties) {
        super();
        this.kafkaProperties = kafkaProperties;
        this.connect(kafkaProperties.getAddress());
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
