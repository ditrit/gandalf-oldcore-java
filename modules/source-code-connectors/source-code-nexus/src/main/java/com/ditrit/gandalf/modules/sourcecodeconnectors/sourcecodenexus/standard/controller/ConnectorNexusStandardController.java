package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.standard.controller;

import com.ditrit.gandalf.modules.abstractconnectors.abstractartifact.controller.ConnectorArtifactStandardController;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.standard.manager.ConnectorNexusStandardManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.properties.ConnectorNexusProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component(value = "standardController")
@ConditionalOnBean(ConnectorNexusProperties.class)
public class ConnectorNexusStandardController extends ConnectorArtifactStandardController {

    private ConnectorNexusStandardManager connectorNexusStandardManager;

    public ConnectorNexusStandardController(ConnectorNexusStandardManager connectorNexusStandardManager) {
        this.connectorNexusStandardManager = connectorNexusStandardManager;
    }

    @Override
    public void command(String command) {

    }

    @Override
    public void event(String event) {

    }
}
