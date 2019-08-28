package com.orness.gandalf.core.module.zeebemodule.custom.worker;

import com.orness.gandalf.core.module.zeebemodule.core.properties.ZeebeProperties;
import com.orness.gandalf.core.module.zeebemodule.custom.manager.ZeebeSpecificManager;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.routing.RunnableRoutingRoutingWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "specificWorkerCommand")
@Profile(value = "zeebe-module")
public class ZeebeSpecificRoutingRoutingWorkerCommand extends RunnableRoutingRoutingWorkerZeroMQ {

    private ZeebeSpecificManager zeebeSpecificManager;
    private ZeebeProperties zeebeProperties;

    @Autowired
    public ZeebeSpecificRoutingRoutingWorkerCommand(ZeebeSpecificManager zeebeSpecificManager, ZeebeProperties zeebeProperties) {
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