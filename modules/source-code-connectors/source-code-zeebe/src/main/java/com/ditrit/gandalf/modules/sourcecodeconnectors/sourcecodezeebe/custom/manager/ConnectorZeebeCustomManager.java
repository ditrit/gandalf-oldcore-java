package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.custom.manager;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodezeebe.properties.ConnectorZeebeProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component(value = "customManager")
@ConditionalOnBean(ConnectorZeebeProperties.class)
public class ConnectorZeebeCustomManager {
}
