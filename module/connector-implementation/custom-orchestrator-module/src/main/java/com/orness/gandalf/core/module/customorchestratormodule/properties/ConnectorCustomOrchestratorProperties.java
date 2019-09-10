package com.orness.gandalf.core.module.customorchestratormodule.properties;

import com.orness.gandalf.core.module.orchestratormodule.properties.ConnectorOrchestratorProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile(value = "custom-orchestrator")
public class ConnectorCustomOrchestratorProperties extends ConnectorOrchestratorProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.endpoint.";

}
