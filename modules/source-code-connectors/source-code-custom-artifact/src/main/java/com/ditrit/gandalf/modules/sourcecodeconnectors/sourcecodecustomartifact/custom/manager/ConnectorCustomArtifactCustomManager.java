package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.custom.manager;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodecustomartifact.properties.ConnectorCustomArtifactProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component(value = "customManager")
@ConditionalOnBean(ConnectorCustomArtifactProperties.class)
public class ConnectorCustomArtifactCustomManager {
}
