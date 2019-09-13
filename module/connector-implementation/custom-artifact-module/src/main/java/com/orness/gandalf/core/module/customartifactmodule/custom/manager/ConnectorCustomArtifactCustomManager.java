package com.orness.gandalf.core.module.customartifactmodule.custom.manager;

import com.orness.gandalf.core.module.customartifactmodule.properties.ConnectorCustomArtifactProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component(value = "customManager")
@ConditionalOnBean(ConnectorCustomArtifactProperties.class)
public class ConnectorCustomArtifactCustomManager {
}
