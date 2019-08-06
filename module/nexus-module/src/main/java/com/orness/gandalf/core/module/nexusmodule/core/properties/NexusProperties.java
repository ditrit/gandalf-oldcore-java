package com.orness.gandalf.core.module.nexusmodule.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "nexus-module")
@EnableConfigurationProperties
@ConfigurationProperties(prefix="artifact")
public class NexusProperties {
}
