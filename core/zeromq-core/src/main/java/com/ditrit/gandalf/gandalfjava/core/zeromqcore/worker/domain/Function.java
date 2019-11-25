package com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain;

import org.zeromq.ZMsg;

import java.util.List;

public abstract class Function {

    public abstract String executeCommand(ZMsg command, List<CommandState> commandStates, ReferenceState referenceState);

    public abstract void executeEvent(ZMsg event);
}
