package com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;
import org.zeromq.ZMsg;

import java.util.List;

public abstract class Function {

    public abstract Constant.Result executeCommand(ZMsg command);

    //TODO
    //public abstract Constant.Result executeCommand(ZMsg command, List<CommandState> commandStates, ReferenceState referenceState);

    public abstract void executeEvent(ZMsg event);
}
