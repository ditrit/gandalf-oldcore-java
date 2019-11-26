package com.ditrit.gandalf.gandalfjava.functions.functionskafka.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.*;
import com.google.gson.Gson;
import org.zeromq.ZMsg;

import java.util.List;

public class FunctionReceiveMessage extends CommandFunction {

    public static final String COMMAND = "RECEIVE_MESSAGE";
    private Gson mapper;

    public FunctionReceiveMessage() {
    }

    @Override
    public String executeCommand(ZMsg command, List<CommandState> commandStates, ReferenceState referenceState) {
        return null;
    }
}
