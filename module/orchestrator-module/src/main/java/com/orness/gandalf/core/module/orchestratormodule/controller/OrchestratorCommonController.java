package com.orness.gandalf.core.module.orchestratormodule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/orchestrator")
public abstract class OrchestratorCommonController {

    @RequestMapping(method = RequestMethod.GET, value = "/register/{service}/{version}")
    public abstract void register(String service, String version);

    @RequestMapping(method = RequestMethod.GET, value = "/unregister/{service}/{version}")
    public abstract void unregister(String service, String version);

    @RequestMapping(method = RequestMethod.GET, value = "/deploy/{service}")
    public abstract void deploy(String service);

    @RequestMapping(method = RequestMethod.GET, value = "/undeploy/{service}")
    public abstract void undeploy(String service);

    @RequestMapping(method = RequestMethod.GET, value = "/start/{service}")
    public abstract void start(String service);

    @RequestMapping(method = RequestMethod.GET, value = "/stop/{service}")
    public abstract void stop(String service);

    @RequestMapping(method = RequestMethod.GET, value = "/scaleup/{service}")
    public abstract void scaleUp(String service);

    @RequestMapping(method = RequestMethod.GET, value = "/scaledown/{service}")
    public abstract void scaleDown(String service);
}
