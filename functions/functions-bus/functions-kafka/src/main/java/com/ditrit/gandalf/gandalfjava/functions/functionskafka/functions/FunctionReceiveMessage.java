package com.ditrit.gandalf.gandalfjava.functions.functionskafka.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.CommandState;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.ReferenceState;
import com.google.gson.Gson;
import org.zeromq.ZMsg;

import java.util.List;

public class FunctionReceiveMessage extends Function {

    public static final String COMMAND = "RECEIVE_MESSAGE";
    private Gson mapper;

    public FunctionReceiveMessage() {
    }

    @Override
    public String executeCommand(ZMsg command, List<CommandState> commandStates, ReferenceState referenceState) {
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
