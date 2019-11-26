package com.ditrit.gandalf.gandalfjava.functions.functionskafka.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandFunction;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandState;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ReferenceState;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ditrit.gandalf.gandalfjava.functions.functionskafka.core.producer.FunctionKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.zeromq.ZMsg;

import java.util.List;

public class FunctionSendMessage extends CommandFunction {

    public static final String COMMAND = "SEND_MESSAGE";
    private Gson mapper;

    @Autowired
    private FunctionKafkaProducer functionKafkaProducer;

    public FunctionSendMessage() {
    }

    @Override
    public String executeCommand(ZMsg command, List<CommandState> commandStates, ReferenceState referenceState) {
        String payload = command.toArray()[14].toString();
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.functionKafkaProducer.sendKafka(jsonObject.get("topic").getAsString(), jsonObject.get("message").getAsString());
        return null;
    }
}
