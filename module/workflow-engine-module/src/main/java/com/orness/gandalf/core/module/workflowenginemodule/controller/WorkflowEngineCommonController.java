package com.orness.gandalf.core.module.workflowenginemodule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.orness.gandalf.core.module.workflowenginemodule.constant.WorkflowEngineConstant.*;

//TODO REVOIR ARGS + URL
@RequestMapping(value = URL_CONTROLLER)
public abstract class WorkflowEngineCommonController {

    @RequestMapping(method = RequestMethod.POST, value = URL_CONTROLLER_DEPLOY)
    public abstract String deployWorkflow(String workflow);

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_INSTANCIATE)
    public abstract void instanciateWorkflow(String id, Object variables);

    @RequestMapping(method = RequestMethod.POST, value = URL_CONTROLLER_SEND)
    public abstract void sendMessage(Object message);
}
