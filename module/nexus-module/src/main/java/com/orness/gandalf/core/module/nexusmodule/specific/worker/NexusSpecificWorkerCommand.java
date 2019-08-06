package com.orness.gandalf.core.module.nexusmodule.specific.worker;

import com.orness.gandalf.core.module.nexusmodule.core.properties.NexusProperties;
import com.orness.gandalf.core.module.nexusmodule.specific.manager.NexusSpecificManager;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

public class NexusSpecificWorkerCommand extends RunnableWorkerZeroMQ {

    private NexusSpecificManager nexusSpecificManager;
    private NexusProperties nexusProperties;

    @Autowired
    public NexusSpecificWorkerCommand(NexusSpecificManager nexusSpecificManager, NexusProperties nexusProperties) {
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
