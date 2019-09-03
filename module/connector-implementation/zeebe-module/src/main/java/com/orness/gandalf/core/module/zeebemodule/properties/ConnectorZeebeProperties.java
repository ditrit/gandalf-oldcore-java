package com.orness.gandalf.core.module.zeebemodule.properties;

import com.orness.gandalf.core.module.workflowenginemodule.properties.ConnectorWorkflowEngineProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile(value = "zeebe")
public class ConnectorZeebeProperties extends ConnectorWorkflowEngineProperties {

}
