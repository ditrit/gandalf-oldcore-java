package com.ditrit.gandalf.gandalfjava.functions.functionskafka.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandFunction;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandState;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ThreadFunction;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ReferenceState;
import com.google.gson.Gson;
import org.springframework.kafka.core.KafkaAdmin;
import org.zeromq.ZMsg;

import java.util.List;

public class FunctionAddSynchronizeTopicGandalf extends CommandFunction {

    public static final String COMMAND = "ADD_SYNCHRONIZE_TOPIC_GANDALF";
    private KafkaAdmin kafkaAdmin;
    private Gson mapper;

    public FunctionAddSynchronizeTopicGandalf() {
        this.mapper = new Gson();
    }

    @Override
    public String executeCommand(ZMsg command, List<CommandState> commandStates, ReferenceState referenceState) {
        return null;
    }
}
