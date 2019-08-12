package com.orness.gandalf.core.module.nexusmodule.common.worker;

import com.orness.gandalf.core.module.nexusmodule.common.manager.NexusCommonManager;
import com.orness.gandalf.core.module.nexusmodule.core.properties.NexusProperties;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;

public class NexusCommonWorkerCommand extends RunnableWorkerZeroMQ {

    private NexusCommonManager nexusCommonManager;
    private NexusProperties nexusProperties;

    @Autowired
    public NexusCommonWorkerCommand(NexusCommonManager nexusCommonManager, NexusProperties nexusProperties) {
        super();
        this.nexusCommonManager = nexusCommonManager;
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
