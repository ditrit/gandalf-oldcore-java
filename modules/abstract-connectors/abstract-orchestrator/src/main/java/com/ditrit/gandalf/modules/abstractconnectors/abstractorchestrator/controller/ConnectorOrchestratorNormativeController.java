package com.ditrit.gandalf.modules.abstractconnectors.abstractorchestrator.controller;

import com.ditrit.gandalf.modules.abstractconnectors.abstractorchestrator.constant.ConnectorOrchestratorConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = ConnectorOrchestratorConstant.URL_CONNECTOR_ORCHESTRATOR_CONTROLLER)
public abstract class ConnectorOrchestratorNormativeController {

    @RequestMapping(method = RequestMethod.POST, value = ConnectorOrchestratorConstant.URL_CONNECTOR_ORCHESTRATOR_CONTROLLER_COMMAND)
    public abstract void command(@RequestBody String command);

    @RequestMapping(method = RequestMethod.POST, value = ConnectorOrchestratorConstant.URL_CONNECTOR_ORCHESTRATOR_CONTROLLER_EVENT)
    public abstract void event(@RequestBody String event);
}
