package com.orness.gandalf.core.module.zeebemodule.specific.worker;

import com.orness.gandalf.core.module.zeebemodule.core.properties.ZeebeProperties;
import com.orness.gandalf.core.module.zeebemodule.specific.manager.ZeebeSpecificManager;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "specificWorkerCommand")
@Profile(value = "zeebe-module")
public class ZeebeSpecificWorkerCommand extends RunnableWorkerZeroMQ {

    private ZeebeSpecificManager zeebeSpecificManager;
    private ZeebeProperties zeebeProperties;

    @Autowired
    public ZeebeSpecificWorkerCommand(ZeebeSpecificManager zeebeSpecificManager, ZeebeProperties zeebeProperties) {
        super();
        this.zeebeSpecificManager = zeebeSpecificManager;
        this.zeebeProperties = zeebeProperties;
        this.connect(zeebeProperties.getWorker());
    }

    @Override
    public Object parse(String messageContent) {
        return null;
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

    }
}
