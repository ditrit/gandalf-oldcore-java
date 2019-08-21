package com.orness.gandalf.core.test.testzeromq.command;

import com.orness.gandalf.core.test.testzeromq.Constant;
import org.zeromq.ZMsg;

public abstract class RunnableCommand extends Command implements Runnable {

    @Override
    public void run() {
        String result;

        while (!Thread.currentThread().isInterrupted()) {
            ZMsg command = this.receiveCommand();
            result = this.executeCommand(command).toString();
            this.sendResultCommand(command, result);
        }
    }

    public abstract Constant.Result executeCommand(ZMsg request);
}
