package com.orness.gandalf.core.module.gitlabmodule.custom.manager;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "customManager")
@Profile(value = "gitlab")
public class ConnectorGitlabCustomManager {
}
