package com.orness.gandalf.core.module.gitmodule.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "git-module")
@EnableConfigurationProperties
@ConfigurationProperties(prefix="git")
public class ConnectorGitProperties {
}
