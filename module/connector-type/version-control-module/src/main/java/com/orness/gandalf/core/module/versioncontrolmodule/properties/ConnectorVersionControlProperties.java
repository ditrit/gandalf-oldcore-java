package com.orness.gandalf.core.module.versioncontrolmodule.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="connector.version.control")
public class ConnectorVersionControlProperties {

}
