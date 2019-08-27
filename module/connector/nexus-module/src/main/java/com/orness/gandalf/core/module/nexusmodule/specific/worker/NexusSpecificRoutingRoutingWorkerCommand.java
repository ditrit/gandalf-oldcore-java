package com.orness.gandalf.core.module.nexusmodule.specific.worker;

import com.orness.gandalf.core.module.nexusmodule.core.properties.NexusProperties;
import com.orness.gandalf.core.module.nexusmodule.specific.manager.NexusSpecificManager;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.routing.RunnableRoutingRoutingWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

public class NexusSpecificRoutingRoutingWorkerCommand extends RunnableRoutingRoutingWorkerZeroMQ {

    private NexusSpecificManager nexusSpecificManager;
    private NexusProperties nexusProperties;

    @Autowired
    public NexusSpecificRoutingRoutingWorkerCommand(NexusSpecificManager nexusSpecificManager, NexusProperties nexusProperties) {
        super();
        this.nexusSpecificManager = nexusSpecificManager;
        this.nexusProperties = nexusProperties;
        this.connect(nexusProperties.getWorker());
    }

    @Override
    public Object parse(String messageContent) {
        return null;
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

    }
}
