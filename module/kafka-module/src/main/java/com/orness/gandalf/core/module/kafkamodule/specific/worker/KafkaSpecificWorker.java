package com.orness.gandalf.core.module.kafkamodule.specific.worker;

import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;

public class KafkaSpecificWorker extends RunnableWorkerZeroMQ {

    public KafkaSpecificWorker(String connection) {
        super(connection);
    }
    //TODO
    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

    }
}
