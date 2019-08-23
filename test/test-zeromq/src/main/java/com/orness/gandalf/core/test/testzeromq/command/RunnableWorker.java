package com.orness.gandalf.core.test.testzeromq.command;

import com.orness.gandalf.core.test.testzeromq.Constant;
import org.zeromq.ZMsg;

public abstract class RunnableWorker extends Worker implements Runnable {

    public void initRunnable(String workerServiceClass, String workerServiceClassType, String frontEndWorkerConnections) {
        this.init(workerServiceClass, workerServiceClassType, frontEndWorkerConnections);
    }

    @Override
    public void run() {
        String result;

        while (!Thread.currentThread().isInterrupted()) {
            ZMsg command = this.receiveCommand();
            result = this.executeCommand(command).toString();
            this.sendResultCommand(command, result);
        }
    }

    public abstract Constant.Result executeCommand(ZMsg command);
}
