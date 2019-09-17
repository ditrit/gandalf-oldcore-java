package com.ditrit.gandalf.modules.abstractconnectors.abstractversioncontrol.controller;

import com.ditrit.gandalf.modules.abstractconnectors.abstractversioncontrol.constant.ConnectorVersionControlConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = ConnectorVersionControlConstant.URL_CONNECTOR_VERSION_CONTROL_CONTROLLER)
public abstract class ConnectorVersionControlNormativeController {

    @RequestMapping(method = RequestMethod.POST, value = ConnectorVersionControlConstant.URL_CONNECTOR_VERSION_CONTROL_CONTROLLER_COMMAND)
    public abstract void command(@RequestBody String command);

    @RequestMapping(method = RequestMethod.POST, value = ConnectorVersionControlConstant.URL_CONNECTOR_VERSION_CONTROL_CONTROLLER_EVENT)
    public abstract void event(@RequestBody String event);

    @RequestMapping(method = RequestMethod.POST, value = ConnectorVersionControlConstant.URL_CONNECTOR_VERSION_CONTROL_CONTROLLER_HOOK)
    public abstract void hook(@RequestBody String hook);
}



