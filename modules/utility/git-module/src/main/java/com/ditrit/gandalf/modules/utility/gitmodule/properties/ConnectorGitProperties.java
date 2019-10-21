package com.ditrit.gandalf.modules.utility.gitmodule.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Configuration
@Profile(value = "git-module")
public class ConnectorGitProperties {

    @Value("${connector.name}")
    private String connectorName;
}
