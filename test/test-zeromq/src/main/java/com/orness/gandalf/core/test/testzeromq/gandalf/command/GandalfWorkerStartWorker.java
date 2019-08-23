package com.orness.gandalf.core.test.testzeromq.gandalf.command;

import com.orness.gandalf.core.test.testzeromq.Constant;
import com.orness.gandalf.core.test.testzeromq.command.RunnableWorker;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.test.testzeromq.gandalf.GandalfConstant.COMMAND_START;

public class GandalfWorkerStartWorker extends RunnableWorker {

    public GandalfWorkerStartWorker(String frontEndCommandConnections) {
        this.initRunnable(COMMAND_START, frontEndCommandConnections);
    }

    @Override
    public Constant.Result executeCommand(ZMsg request) {
        System.out.println("COMMAND START !!!");
        return Constant.Result.SUCCESS;
    }
}
