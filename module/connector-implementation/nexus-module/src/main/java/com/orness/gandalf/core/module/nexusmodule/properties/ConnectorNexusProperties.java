package com.orness.gandalf.core.module.nexusmodule.properties;

import com.orness.gandalf.core.module.artifactmodule.properties.ConnectorArtifactProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile(value = "nexus")
public class ConnectorNexusProperties extends ConnectorArtifactProperties {

}
