package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodejenkins.properties;

import com.orness.gandalf.core.module.orchestratormodule.properties.ConnectorOrchestratorProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnExpression("#{'${${instance.name}.connectors.${connector.type}.${connector.name}.target.type}' == 'jenkins'}")
public class ConnectorJenkinsProperties extends ConnectorOrchestratorProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.endpoint.";

}
