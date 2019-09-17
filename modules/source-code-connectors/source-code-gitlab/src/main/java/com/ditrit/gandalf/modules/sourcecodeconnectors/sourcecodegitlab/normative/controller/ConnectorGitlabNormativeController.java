package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.normative.controller;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.normative.manager.ConnectorGitlabNormativeManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodegitlab.properties.ConnectorGitlabProperties;
import com.ditrit.gandalf.modules.abstractconnectors.abstractversioncontrol.controller.ConnectorVersionControlNormativeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "normativeController")
@ConditionalOnBean(ConnectorGitlabProperties.class)
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
    public void hook(@RequestBody String hook) {
        this.connectorGitlabNormativeManager.hookMerge(hook);
    }
}


