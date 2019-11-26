package com.ditrit.gandalf.gandalfjava.functions.functionskafka.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandFunction;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandState;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ReferenceState;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ditrit.gandalf.gandalfjava.functions.functionskafka.core.producer.FunctionKafkaProducer;
import org.springframework.kafka.core.KafkaAdmin;
import org.zeromq.ZMsg;

import java.util.List;

public class FunctionSynchronizeTopicKafka extends CommandFunction {

    public static final String COMMAND = "SYNCHRONIZE_TOPIC_KAFKA";
    private KafkaAdmin kafkaAdmin;
    private Gson mapper;
    private FunctionKafkaProducer functionKafkaProducer;

    public FunctionSynchronizeTopicKafka() {
    }

    @Override
    public String executeCommand(ZMsg command, List<CommandState> commandStates, ReferenceState referenceState) {
        Object[] commandArray = command.toArray();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("event", commandArray[5].toString());
        jsonObject.addProperty("payload", commandArray[6].toString());

        this.functionKafkaProducer.sendKafka(commandArray[3].toString(), jsonObject.toString());

        return null;
    }
}
