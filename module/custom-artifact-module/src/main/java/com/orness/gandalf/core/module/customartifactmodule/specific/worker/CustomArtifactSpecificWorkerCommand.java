package com.orness.gandalf.core.module.customartifactmodule.specific.worker;

import com.orness.gandalf.core.module.customartifactmodule.config.properties.CustomArtifactProperties;
import com.orness.gandalf.core.module.customartifactmodule.specific.manager.CustomArtifactSpecificManager;
import com.orness.gandalf.core.module.zeromqmodule.command.domain.MessageCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "specificWorkerCommand")
@Profile(value = "custom-artifact-module")
public class CustomArtifactSpecificWorkerCommand extends RunnableWorkerZeroMQ {

    private CustomArtifactSpecificManager customArtifactSpecificManager;
    private CustomArtifactProperties customArtifactProperties;

    @Autowired
    public CustomArtifactSpecificWorkerCommand(CustomArtifactSpecificManager customArtifactSpecificManager, CustomArtifactProperties customArtifactProperties) {
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
