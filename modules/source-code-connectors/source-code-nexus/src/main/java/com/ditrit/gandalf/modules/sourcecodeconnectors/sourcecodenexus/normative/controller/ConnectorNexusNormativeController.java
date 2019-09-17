package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.normative.controller;

import com.ditrit.gandalf.modules.abstractconnectors.abstractartifact.controller.ConnectorArtifactNormativeController;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.normative.manager.ConnectorNexusNormativeManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.properties.ConnectorNexusProperties;
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
