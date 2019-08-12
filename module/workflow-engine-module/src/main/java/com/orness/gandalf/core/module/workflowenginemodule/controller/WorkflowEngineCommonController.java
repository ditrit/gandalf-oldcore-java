package com.orness.gandalf.core.module.workflowenginemodule.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.orness.gandalf.core.module.workflowenginemodule.constant.WorkflowEngineConstant.*;

@RequestMapping(value = URL_CONTROLLER)
public abstract class WorkflowEngineCommonController {

    @RequestMapping(method = RequestMethod.POST, value = URL_CONTROLLER_DEPLOY)
    public abstract String deployWorkflow(@RequestBody  String workflow);

    @RequestMapping(method = RequestMethod.POST, value = URL_CONTROLLER_INSTANCIATE)
    public abstract void instanciateWorkflow(@PathVariable("id") String id, @RequestBody Object variables);

    @RequestMapping(method = RequestMethod.POST, value = URL_CONTROLLER_SEND)
    public abstract void sendMessage(@RequestBody Object message);
}
