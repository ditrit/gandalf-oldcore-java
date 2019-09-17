package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.properties;

import com.ditrit.gandalf.modules.abstractconnectors.abstractworkflowengine.properties.ConnectorWorkflowEngineProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnExpression("#{'${${instance.name}.connectors.${connector.type}.${connector.name}.target.type}' == 'zeebe'}")
public class ConnectorZeebeProperties extends ConnectorWorkflowEngineProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.endpoint.";

}
