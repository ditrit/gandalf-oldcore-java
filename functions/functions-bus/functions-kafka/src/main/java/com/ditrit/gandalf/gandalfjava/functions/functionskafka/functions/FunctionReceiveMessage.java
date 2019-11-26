package com.ditrit.gandalf.gandalfjava.functions.functionskafka.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import org.zeromq.ZMsg;

public class FunctionReceiveMessage extends Function {

    public static final String COMMAND = "RECEIVE_MESSAGE";
    private Gson mapper;

    public FunctionReceiveMessage() {
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
