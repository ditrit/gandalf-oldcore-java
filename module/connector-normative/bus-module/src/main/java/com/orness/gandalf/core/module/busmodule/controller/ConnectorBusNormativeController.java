package com.orness.gandalf.core.module.busmodule.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.orness.gandalf.core.module.busmodule.constant.ConnectorBusConstant.*;


//TODO REVOIR AGRS
@RequestMapping(value = URL_CONNECTOR_BUS_CONTROLLER)
public abstract class ConnectorBusNormativeController {

    @RequestMapping(method = RequestMethod.POST, value = URL_CONNECTOR_BUS_CONTROLLER_COMMAND)
    public abstract void command(@RequestBody String command);

    @RequestMapping(method = RequestMethod.POST, value = URL_CONNECTOR_BUS_CONTROLLER_EVENT)
    public abstract void event(@RequestBody String event);

}
