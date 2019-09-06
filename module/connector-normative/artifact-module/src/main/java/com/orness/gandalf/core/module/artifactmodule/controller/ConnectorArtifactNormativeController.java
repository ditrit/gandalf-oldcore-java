package com.orness.gandalf.core.module.artifactmodule.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static com.orness.gandalf.core.module.artifactmodule.constant.ConnectorArtifactConstant.*;

@RequestMapping(value = URL_CONNECTOR_ARTIFACT_CONTROLLER)
public abstract class ConnectorArtifactNormativeController {

    @RequestMapping(method = RequestMethod.POST, value = URL_CONNECTOR_ARTIFACT_CONTROLLER_COMMAND)
    public abstract void command(@RequestBody String command);

    @RequestMapping(method = RequestMethod.POST, value = URL_CONNECTOR_ARTIFACT_CONTROLLER_EVENT)
    public abstract void event(@RequestBody String event);
}
