package com.orness.gandalf.core.module.nexusmodule.custom.manager;

import com.orness.gandalf.core.module.nexusmodule.properties.ConnectorNexusProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component(value = "customManager")
@ConditionalOnBean(ConnectorNexusProperties.class)
public class ConnectorNexusCustomManager {
}
