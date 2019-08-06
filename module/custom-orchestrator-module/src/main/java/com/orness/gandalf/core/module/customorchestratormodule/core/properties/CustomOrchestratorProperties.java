package com.orness.gandalf.core.module.customorchestratormodule.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "custom-orchestrator-module")
@EnableConfigurationProperties
@ConfigurationProperties(prefix="custom.orchestrator")
public class CustomOrchestratorProperties {
}
