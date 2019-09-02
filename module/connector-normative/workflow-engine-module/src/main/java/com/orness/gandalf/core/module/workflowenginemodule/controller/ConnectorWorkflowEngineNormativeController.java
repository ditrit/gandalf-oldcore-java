package com.orness.gandalf.core.module.workflowenginemodule.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.orness.gandalf.core.module.workflowenginemodule.constant.ConnectorWorkflowEngineConstant.*;

@RequestMapping(value = URL_CONNECTOR_WORKFLOWENGINE_CONTROLLER)
public abstract class ConnectorWorkflowEngineNormativeController {

    @RequestMapping(method = RequestMethod.POST, value = URL_CONNECTOR_WORKFLOWENGINE_CONTROLLER_COMMAND)
    public abstract String command(@RequestBody String command);

    @RequestMapping(method = RequestMethod.POST, value = URL_CONNECTOR_WORKFLOWENGINE_CONTROLLER_EVENT)
    public abstract void event(@RequestBody String event);
}

