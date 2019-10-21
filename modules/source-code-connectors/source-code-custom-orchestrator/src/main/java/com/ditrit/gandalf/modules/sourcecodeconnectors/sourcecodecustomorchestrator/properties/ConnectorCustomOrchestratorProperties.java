package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomorchestrator.properties;

import com.ditrit.gandalf.modules.abstractconnectors.abstractorchestrator.properties.ConnectorOrchestratorProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnExpression("#{'${${instance.name}.connectors.${connector.type}.${connector.name}.target.type}' == 'custom-orchestrator'}")
public class ConnectorCustomOrchestratorProperties extends ConnectorOrchestratorProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.endpoint.";

}
