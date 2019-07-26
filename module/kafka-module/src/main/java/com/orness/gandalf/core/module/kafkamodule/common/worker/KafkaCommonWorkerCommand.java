package com.orness.gandalf.core.module.kafkamodule.common.worker;

import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;

public class KafkaCommonWorkerCommand extends RunnableWorkerZeroMQ {

    public KafkaCommonWorkerCommand(String connection) {
        super(connection);
    }
    //TODO
    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

    }
}
