package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.custom.manager;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.properties.ConnectorGitlabProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component(value = "customManager")
@ConditionalOnBean(ConnectorGitlabProperties.class)
public class ConnectorGitlabCustomManager {
}
