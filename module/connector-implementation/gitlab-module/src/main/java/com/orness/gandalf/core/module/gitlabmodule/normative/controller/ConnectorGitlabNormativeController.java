package com.orness.gandalf.core.module.gitlabmodule.normative.controller;

import com.orness.gandalf.core.module.gitlabmodule.normative.manager.ConnectorGitlabNormativeManager;
import com.orness.gandalf.core.module.versioncontrolmodule.controller.ConnectorVersionControlNormativeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "normativeController")
@Profile(value = "gitlab")
public class ConnectorGitlabNormativeController extends ConnectorVersionControlNormativeController {

    private ConnectorGitlabNormativeManager connectorGitlabNormativeManager;

    @Autowired
    public ConnectorGitlabNormativeController(ConnectorGitlabNormativeManager connectorGitlabNormativeManager) {
        this.connectorGitlabNormativeManager = connectorGitlabNormativeManager;
    }

    @Override
    public void command(String command) {

    }

    @Override
    public void event(String event) {

    }

    @Override
    public void hook(String hook) {
        this.connectorGitlabNormativeManager.hookMerge(hook);
    }
}
