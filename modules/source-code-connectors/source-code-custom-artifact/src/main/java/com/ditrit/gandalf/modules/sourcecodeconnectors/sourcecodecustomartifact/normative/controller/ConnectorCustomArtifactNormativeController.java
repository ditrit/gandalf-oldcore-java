package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.normative.controller;

import com.ditrit.gandalf.modules.abstractconnectors.abstractartifact.controller.ConnectorArtifactNormativeController;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.normative.manager.ConnectorCustomArtifactNormativeManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.properties.ConnectorCustomArtifactProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

@RestController(value = "normativeController")
@ConditionalOnBean(ConnectorCustomArtifactProperties.class)
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