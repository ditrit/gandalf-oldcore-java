package com.ditrit.gandalf.core.zeromqcore.command.router;

import org.zeromq.ZMsg;

public abstract class ClusterCommandRouter {

    public abstract ZMsg getCommandTarget(ZMsg command);
}
