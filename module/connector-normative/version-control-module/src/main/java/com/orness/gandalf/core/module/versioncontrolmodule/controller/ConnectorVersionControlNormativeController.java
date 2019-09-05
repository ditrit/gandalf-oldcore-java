package com.orness.gandalf.core.module.versioncontrolmodule.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.orness.gandalf.core.module.versioncontrolmodule.constant.ConnectorVersionControlConstant.*;

@RequestMapping(value = URL_CONNECTOR_VERSION_CONTROL_CONTROLLER)
public abstract class ConnectorVersionControlNormativeController {

    @RequestMapping(method = RequestMethod.POST, value = URL_CONNECTOR_VERSION_CONTROL_CONTROLLER_COMMAND)
    public abstract void command(@RequestBody String command);

    @RequestMapping(method = RequestMethod.POST, value = URL_CONNECTOR_VERSION_CONTROL_CONTROLLER_EVENT)
    public abstract void event(@RequestBody String event);

    @RequestMapping(method = RequestMethod.POST, value = URL_CONNECTOR_VERSION_CONTROL_CONTROLLER_HOOK)
    public abstract void hook(@RequestBody String hook);
}
