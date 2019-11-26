package com.ditrit.gandalf.gandalfjava.functions.functionskafka.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import org.springframework.kafka.core.KafkaAdmin;
import org.zeromq.ZMsg;

public class FunctionAddSynchronizeTopicKafka extends Function {

    public static final String COMMAND = "ADD_SYNCHRONIZE_TOPIC_KAFKA";
    private KafkaAdmin kafkaAdmin;
    private Gson mapper;

    public FunctionAddSynchronizeTopicKafka() {
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
