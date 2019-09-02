package com.orness.gandalf.core.module.orchestratormodule.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.orness.gandalf.core.module.orchestratormodule.constant.ConnectorOrchestratorConstant.*;

@RequestMapping(value = URL_CONNECTOR_ORCHESTRATOR_CONTROLLER)
public abstract class ConnectorOrchestratorNormativeController {

    @RequestMapping(method = RequestMethod.POST, value = URL_CONNECTOR_ORCHESTRATOR_CONTROLLER_COMMAND)
    public abstract void command(@RequestBody String command);

    @RequestMapping(method = RequestMethod.POST, value = URL_CONNECTOR_ORCHESTRATOR_CONTROLLER_EVENT)
    public abstract void event(@RequestBody String event);
}
