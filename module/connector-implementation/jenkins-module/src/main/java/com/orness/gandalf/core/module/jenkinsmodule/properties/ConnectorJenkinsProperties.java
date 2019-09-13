package com.orness.gandalf.core.module.jenkinsmodule.properties;

import com.orness.gandalf.core.module.orchestratormodule.properties.ConnectorOrchestratorProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value="target.type", havingValue = "jenkins")
public class ConnectorJenkinsProperties extends ConnectorOrchestratorProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.endpoint.";

}
