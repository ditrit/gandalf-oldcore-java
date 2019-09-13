package com.orness.gandalf.core.module.gitlabmodule.custom.manager;

import com.orness.gandalf.core.module.gitlabmodule.properties.ConnectorGitlabProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component(value = "customManager")
@ConditionalOnBean(ConnectorGitlabProperties.class)
public class ConnectorGitlabCustomManager {
}
