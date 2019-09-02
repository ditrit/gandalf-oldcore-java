package com.orness.gandalf.core.module.customartifactmodule.normative.controller;

import com.orness.gandalf.core.module.artifactmodule.controller.ConnectorArtifactNormativeController;
import com.orness.gandalf.core.module.customartifactmodule.normative.manager.ConnectorCustomArtifactNormativeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

@RestController(value = "normativeController")
@Profile(value = "custom-artifact")
public class ConnectorCustomArtifactNormativeController extends ConnectorArtifactNormativeController {

    private ConnectorCustomArtifactNormativeManager connectorCustomArtifactNormativeManager;

    @Autowired
    public ConnectorCustomArtifactNormativeController(ConnectorCustomArtifactNormativeManager connectorCustomArtifactNormativeManager) {
        this.connectorCustomArtifactNormativeManager = connectorCustomArtifactNormativeManager;

    }

    @Override
    public void command(String command) {

    }

    @Override
    public void event(String event) {

    }
}