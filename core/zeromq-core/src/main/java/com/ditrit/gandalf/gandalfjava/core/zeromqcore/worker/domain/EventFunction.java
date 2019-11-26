package com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain;

import org.zeromq.ZMsg;

public abstract class EventFunction extends ThreadFunction {

    public abstract void executeEvent(ZMsg event);
}
