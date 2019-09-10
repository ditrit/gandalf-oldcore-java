package com.orness.gandalf.core.module.jenkinsmodule.properties;

import com.orness.gandalf.core.module.orchestratormodule.properties.ConnectorOrchestratorProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = "jenkins")
public class ConnectorJenkinsProperties extends ConnectorOrchestratorProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.";

}