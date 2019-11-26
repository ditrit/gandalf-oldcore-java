package com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain;

import org.zeromq.ZMsg;

import java.util.List;

public abstract class CommandFunction extends ThreadFunction {

    public abstract String executeCommand(ZMsg command, List<CommandState> commandStates, ReferenceState referenceState);
}
