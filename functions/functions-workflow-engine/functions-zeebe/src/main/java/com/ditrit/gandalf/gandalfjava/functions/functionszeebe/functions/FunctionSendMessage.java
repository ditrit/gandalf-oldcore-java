package com.ditrit.gandalf.gandalfjava.functions.functionszeebe.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandState;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ReferenceState;
import com.google.gson.Gson;
import com.ditrit.gandalf.gandalfjava.functions.functionszeebe.core.domain.ConnectorZeebeMessage;
import io.zeebe.client.ZeebeClient;
import org.zeromq.ZMsg;

import java.time.Duration;
import java.util.List;

public class FunctionSendMessage extends Function {

    private Gson mapper;
    private ZeebeClient zeebe;

    public FunctionSendMessage() {
        super();
        this.mapper = new Gson();
    }

    @Override
    public String executeCommand(ZMsg command, List<CommandState> commandStates, ReferenceState referenceState) {
        String payload = command.toArray()[14].toString();
        ConnectorZeebeMessage connectorZeebeMessage = this.mapper.fromJson(payload, ConnectorZeebeMessage.class);
        zeebe.newPublishMessageCommand()
                .messageName(connectorZeebeMessage.getName())
                .correlationKey(connectorZeebeMessage.getCorrelationKey())
                .variables(connectorZeebeMessage.getVariables())
                .timeToLive(Duration.ofMinutes(connectorZeebeMessage.getDuration()))
                .send().join();
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
