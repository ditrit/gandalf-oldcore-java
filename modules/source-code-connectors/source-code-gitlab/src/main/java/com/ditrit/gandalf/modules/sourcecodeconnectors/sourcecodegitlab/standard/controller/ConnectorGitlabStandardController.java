package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.standard.controller;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.standard.manager.ConnectorGitlabStandardManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.properties.ConnectorGitlabProperties;
import com.ditrit.gandalf.modules.abstractconnectors.abstractversioncontrol.controller.ConnectorVersionControlStandardController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "standardController")
@ConditionalOnBean(ConnectorGitlabProperties.class)
public class ConnectorGitlabStandardController extends ConnectorVersionControlStandardController {

    private ConnectorGitlabStandardManager connectorGitlabStandardManager;

    @Autowired
    public ConnectorGitlabStandardController(ConnectorGitlabStandardManager connectorGitlabStandardManager) {
        this.connectorGitlabStandardManager = connectorGitlabStandardManager;
    }

    @Override
    public void command(String command) {

    }

    @Override
    public void event(String event) {

    }

    @Override
    public void hook(@RequestBody String hook) {
        this.connectorGitlabStandardManager.hookMerge(hook);
    }
}


