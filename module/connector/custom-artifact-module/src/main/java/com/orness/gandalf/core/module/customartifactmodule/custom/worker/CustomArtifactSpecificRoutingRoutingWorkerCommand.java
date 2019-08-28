package com.orness.gandalf.core.module.customartifactmodule.custom.worker;

import com.orness.gandalf.core.module.customartifactmodule.core.properties.CustomArtifactProperties;
import com.orness.gandalf.core.module.customartifactmodule.custom.manager.CustomArtifactSpecificManager;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.routing.RunnableRoutingRoutingWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "specificWorkerCommand")
@Profile(value = "custom-artifact-module")
public class CustomArtifactSpecificRoutingRoutingWorkerCommand extends RunnableRoutingRoutingWorkerZeroMQ {

    private CustomArtifactSpecificManager customArtifactSpecificManager;
    private CustomArtifactProperties customArtifactProperties;

    @Autowired
    public CustomArtifactSpecificRoutingRoutingWorkerCommand(CustomArtifactSpecificManager customArtifactSpecificManager, CustomArtifactProperties customArtifactProperties) {
        super();
        this.customArtifactSpecificManager = customArtifactSpecificManager;
        this.customArtifactProperties = customArtifactProperties;
        this.connect(this.customArtifactProperties.getWorker());
    }

    @Override
    public Object parse(String messageContent) {
        return null;
    }

    @Override
    public void command(MessageCommandZeroMQ messageCommandZeroMQ) {

    }
}
