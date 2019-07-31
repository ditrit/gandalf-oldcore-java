package com.orness.gandalf.core.module.orchestratormodule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.orness.gandalf.core.module.orchestratormodule.constant.OrchestratorConstant.*;

@RequestMapping(value = URL_CONTROLLER)
public abstract class OrchestratorCommonController {

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_REGISTER)
    public abstract void register(String service, String version);

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_UNREGISTER)
    public abstract void unregister(String service, String version);

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_DEPLOY)
    public abstract void deploy(String service);

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_UNDEPLOY)
    public abstract void undeploy(String service);

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_START)
    public abstract void start(String service);

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_STOP)
    public abstract void stop(String service);

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_SCALE_UP)
    public abstract void scaleUp(String service);

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_SCALE_DOWN)
    public abstract void scaleDown(String service);
}
