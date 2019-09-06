package com.orness.gandalf.core.module.gitlabmodule.properties;

import com.orness.gandalf.core.module.versioncontrolmodule.properties.ConnectorVersionControlProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
@Profile(value = "gitlab")
public class ConnectorGitlabProperties extends ConnectorVersionControlProperties {

}
