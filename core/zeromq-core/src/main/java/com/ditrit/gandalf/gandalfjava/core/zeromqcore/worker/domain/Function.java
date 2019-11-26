package com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain;

import org.zeromq.ZMsg;

import java.util.List;

public abstract class Function extends Thread {

    public void run() {
        while(!this.isInterrupted()) {

        }
    }

    //public abstract Constant.Result executeCommand(ZMsg command);

    public abstract String executeCommand(ZMsg command, List<CommandState> commandStates, ReferenceState referenceState);

    public abstract void executeEvent(ZMsg event);
}
