package com.orness.gandalf.core.module.customartifactmodule.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "custom-artifact-module")
@EnableConfigurationProperties
@ConfigurationProperties(prefix="custom.artifact")
public class CustomArtifactProperties {
}
