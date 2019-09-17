package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.properties;

import com.ditrit.gandalf.modules.abstractconnectors.abstractartifact.properties.ConnectorArtifactProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnExpression("#{'${${instance.name}.connectors.${connector.type}.${connector.name}.target.type}' == 'custom-artifact'}")
public class ConnectorCustomArtifactProperties extends ConnectorArtifactProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.endpoint.";

}
