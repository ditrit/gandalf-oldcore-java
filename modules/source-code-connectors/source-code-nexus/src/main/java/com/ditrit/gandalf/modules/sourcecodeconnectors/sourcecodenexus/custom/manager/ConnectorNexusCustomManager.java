package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.custom.manager;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodenexus.properties.ConnectorNexusProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component(value = "customManager")
@ConditionalOnBean(ConnectorNexusProperties.class)
public class ConnectorNexusCustomManager {
}
