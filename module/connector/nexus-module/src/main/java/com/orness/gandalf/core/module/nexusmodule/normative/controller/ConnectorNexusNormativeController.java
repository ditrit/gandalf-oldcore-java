package com.orness.gandalf.core.module.nexusmodule.normative.controller;

import com.orness.gandalf.core.module.artifactmodule.controller.ConnectorArtifactNormativeController;
import com.orness.gandalf.core.module.nexusmodule.normative.manager.ConnectorNexusNormativeManager;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "normativeController")
@Profile(value = "nexus")
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
