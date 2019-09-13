package com.orness.gandalf.core.module.gitlabmodule.properties;

import com.orness.gandalf.core.module.versioncontrolmodule.properties.ConnectorVersionControlProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value="target.type", havingValue = "gitlab")
public class ConnectorGitlabProperties extends ConnectorVersionControlProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.endpoint.";

}
