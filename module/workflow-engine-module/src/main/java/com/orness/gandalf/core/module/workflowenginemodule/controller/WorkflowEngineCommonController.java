package com.orness.gandalf.core.module.workflowenginemodule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//TODO REVOIR ARGS
@RequestMapping("/workflowengine")
public abstract class WorkflowEngineCommonController {

    @RequestMapping(method = RequestMethod.POST, value = "/deploy/")
    public abstract String deployWorkflow(String workflow);

    @RequestMapping(method = RequestMethod.GET, value = "/instanciate/")
    public abstract void instanciateWorkflow(String id, Object variables);

    @RequestMapping(method = RequestMethod.POST, value = "/send/")
    public abstract void sendMessage(Object message);
}
