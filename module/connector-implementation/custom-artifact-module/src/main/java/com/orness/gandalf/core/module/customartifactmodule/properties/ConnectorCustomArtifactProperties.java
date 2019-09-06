package com.orness.gandalf.core.module.customartifactmodule.properties;

import com.orness.gandalf.core.module.artifactmodule.properties.ConnectorArtifactProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile(value = "custom-artifact")
public class ConnectorCustomArtifactProperties extends ConnectorArtifactProperties {

}
