package com.ditrit.gandalf.modules.abstractconnectors.abstractbus.controller;

import com.ditrit.gandalf.modules.abstractconnectors.abstractbus.constant.ConnectorBusConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


//TODO REVOIR AGRS
@RequestMapping(value = ConnectorBusConstant.URL_CONNECTOR_BUS_CONTROLLER)
public abstract class ConnectorBusStandardController {

    @RequestMapping(method = RequestMethod.POST, value = ConnectorBusConstant.URL_CONNECTOR_BUS_CONTROLLER_COMMAND)
    public abstract void command(@RequestBody String command);

    @RequestMapping(method = RequestMethod.POST, value = ConnectorBusConstant.URL_CONNECTOR_BUS_CONTROLLER_EVENT)
    public abstract void event(@RequestBody String event);

}
