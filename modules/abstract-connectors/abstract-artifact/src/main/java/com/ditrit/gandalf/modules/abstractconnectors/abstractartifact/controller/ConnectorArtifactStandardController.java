package com.ditrit.gandalf.modules.abstractconnectors.abstractartifact.controller;

import com.ditrit.gandalf.modules.abstractconnectors.abstractartifact.constant.ConnectorArtifactConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = ConnectorArtifactConstant.URL_CONNECTOR_ARTIFACT_CONTROLLER)
public abstract class ConnectorArtifactStandardController {

    @RequestMapping(method = RequestMethod.POST, value = ConnectorArtifactConstant.URL_CONNECTOR_ARTIFACT_CONTROLLER_COMMAND)
    public abstract void command(@RequestBody String command);

    @RequestMapping(method = RequestMethod.POST, value = ConnectorArtifactConstant.URL_CONNECTOR_ARTIFACT_CONTROLLER_EVENT)
    public abstract void event(@RequestBody String event);
}
