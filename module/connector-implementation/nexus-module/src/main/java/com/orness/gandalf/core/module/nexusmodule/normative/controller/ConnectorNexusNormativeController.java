package com.orness.gandalf.core.module.nexusmodule.normative.controller;

import com.orness.gandalf.core.module.artifactmodule.controller.ConnectorArtifactNormativeController;
import com.orness.gandalf.core.module.nexusmodule.normative.manager.ConnectorNexusNormativeManager;
import com.orness.gandalf.core.module.nexusmodule.properties.ConnectorNexusProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component(value = "normativeController")
@ConditionalOnBean(ConnectorNexusProperties.class)
public class ConnectorNexusNormativeController extends ConnectorArtifactNormativeController {

    private ConnectorNexusNormativeManager connectorNexusNormativeManager;

    public ConnectorNexusNormativeController(ConnectorNexusNormativeManager connectorNexusNormativeManager) {
        this.connectorNexusNormativeManager = connectorNexusNormativeManager;
    }

    @Override
    public void command(String command) {

    }

    @Override
    public void event(String event) {

    }
}
