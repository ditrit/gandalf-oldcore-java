package com.orness.gandalf.core.module.gitlabmodule.properties;

import com.orness.gandalf.core.module.versioncontrolmodule.properties.ConnectorVersionControlProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnExpression("#{'${${instance.name}.connectors.${connector.type}.${connector.name}.target.type}' == 'gitlab'}")
public class ConnectorGitlabProperties extends ConnectorVersionControlProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.endpoint.";

}
