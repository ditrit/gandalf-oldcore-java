package com.ditrit.gandalf.core.zeromqcore.worker.domain;

import com.ditrit.gandalf.core.zeromqcore.constant.Constant;
import org.zeromq.ZMsg;

public abstract class Function {

    public abstract Constant.Result executeCommand(ZMsg command);

    public abstract void executeEvent(ZMsg event);
}
