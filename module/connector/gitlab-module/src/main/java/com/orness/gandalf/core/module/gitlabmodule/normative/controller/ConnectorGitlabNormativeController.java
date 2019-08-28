package com.orness.gandalf.core.module.gitlabmodule.normative.controller;

import com.orness.gandalf.core.module.gitlabmodule.normative.manager.ConnectorGitlabNormativeManager;
import com.orness.gandalf.core.module.versioncontrolmodule.controller.ConnectorVersionControlNormativeController;
import org.springframework.beans.factory.annotation.Autowired;

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
}
