package com.ditrit.gandalf.gandalfjava.functions.functionsjenkins.properties;

import com.ditrit.gandalf.gandalfjava.core.connectorcore.properties.ConnectorProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnExpression("#{'${${instance.name}.connectors.${connector.type}.${connector.name}.target.type}' == 'custom-orchestrator'}")
public class ConnectorCustomOrchestratorProperties extends ConnectorProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.endpoint.";

}