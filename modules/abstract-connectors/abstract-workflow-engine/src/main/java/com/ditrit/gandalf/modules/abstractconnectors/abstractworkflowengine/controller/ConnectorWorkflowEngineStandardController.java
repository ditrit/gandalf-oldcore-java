package com.ditrit.gandalf.modules.abstractconnectors.abstractworkflowengine.controller;

import com.ditrit.gandalf.modules.abstractconnectors.abstractworkflowengine.constant.ConnectorWorkflowEngineConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = ConnectorWorkflowEngineConstant.URL_CONNECTOR_WORKFLOWENGINE_CONTROLLER)
public abstract class ConnectorWorkflowEngineStandardController {

    @RequestMapping(method = RequestMethod.POST, value = ConnectorWorkflowEngineConstant.URL_CONNECTOR_WORKFLOWENGINE_CONTROLLER_COMMAND)
    public abstract String command(@RequestBody String command);

    @RequestMapping(method = RequestMethod.POST, value = ConnectorWorkflowEngineConstant.URL_CONNECTOR_WORKFLOWENGINE_CONTROLLER_EVENT)
    public abstract void event(@RequestBody String event);
}

