package com.orness.gandalf.core.module.zeebemodule.properties;

import com.orness.gandalf.core.module.workflowenginemodule.properties.ConnectorWorkflowEngineProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(value="target.type", havingValue = "zeebe")
public class ConnectorZeebeProperties extends ConnectorWorkflowEngineProperties {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.target.endpoint.";

}
