package com.orness.gandalf.core.test.testzeromq.gandalf.worker;

import com.orness.gandalf.core.test.testzeromq.Constant;
import com.orness.gandalf.core.test.testzeromq.command.RunnableWorker;
import org.zeromq.ZMsg;

import static com.orness.gandalf.core.test.testzeromq.gandalf.GandalfConstant.EVENT_COMMAND_START;

public class GandalfWorkerStartEvent extends RunnableWorker {

    public GandalfWorkerStartEvent(String frontEndCommandConnections) {
       this.initRunnable(EVENT_COMMAND_START, frontEndCommandConnections);
    }

    @Override
    public Constant.Result executeCommand(ZMsg request) {
        System.out.println("EVENT START !!!");
        return Constant.Result.SUCCESS;
    }
}
