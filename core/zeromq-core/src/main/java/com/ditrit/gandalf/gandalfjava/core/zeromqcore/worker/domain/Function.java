package com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain;

import org.zeromq.ZMsg;

import java.util.List;

public abstract class Function extends Thread {

    public void run() {
        System.out.println("Start");
        while(!this.isInterrupted()) {
            System.out.println("Running");
        }
        System.out.println("Stop");
    }

    public abstract String executeCommand(ZMsg command, List<CommandState> commandStates, ReferenceState referenceState);

    public abstract void executeEvent(ZMsg event);
}
