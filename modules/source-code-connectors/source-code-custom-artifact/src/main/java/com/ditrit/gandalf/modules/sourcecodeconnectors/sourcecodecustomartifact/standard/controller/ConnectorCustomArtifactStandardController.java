package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.standard.controller;

import com.ditrit.gandalf.modules.abstractconnectors.abstractartifact.controller.ConnectorArtifactStandardController;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.standard.manager.ConnectorCustomArtifactStandardManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.properties.ConnectorCustomArtifactProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

@RestController(value = "standardController")
@ConditionalOnBean(ConnectorCustomArtifactProperties.class)
public class ConnectorCustomArtifactStandardController extends ConnectorArtifactStandardController {

    private ConnectorCustomArtifactStandardManager connectorCustomArtifactStandardManager;

    @Autowired
    public ConnectorCustomArtifactStandardController(ConnectorCustomArtifactStandardManager connectorCustomArtifactStandardManager) {
        this.connectorCustomArtifactStandardManager = connectorCustomArtifactStandardManager;

    }

    @Override
    public void command(String command) {

    }

    @Override
    public void event(String event) {

    }
}