package com.orness.gandalf.core.module.zeebemodule.custom.manager;

import com.orness.gandalf.core.module.zeebemodule.properties.ConnectorZeebeProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component(value = "customManager")
@ConditionalOnBean(ConnectorZeebeProperties.class)
public class ConnectorZeebeCustomManager {
}
