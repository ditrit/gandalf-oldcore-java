package com.orness.gandalf.core.module.nexusmodule.properties;

import com.orness.gandalf.core.module.artifactmodule.properties.ConnectorArtifactProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile(value = "nexus")
public class ConnectorNexusProperties extends ConnectorArtifactProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.";

}
