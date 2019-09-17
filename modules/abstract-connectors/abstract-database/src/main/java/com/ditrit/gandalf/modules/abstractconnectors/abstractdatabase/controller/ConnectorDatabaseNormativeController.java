package com.ditrit.gandalf.modules.abstractconnectors.abstractdatabase.controller;

import com.ditrit.gandalf.modules.abstractconnectors.abstractdatabase.constant.ConnectorDatabaseConstant;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping(value = ConnectorDatabaseConstant.URL_CONNECTOR_DATABASE_CONTROLLER)
public abstract class ConnectorDatabaseNormativeController {

    @RequestMapping(method = RequestMethod.POST, value = ConnectorDatabaseConstant.URL_CONNECTOR_DATABASE_CONTROLLER_COMMAND)
    public abstract void command(@RequestBody String command);

    @RequestMapping(method = RequestMethod.POST, value = ConnectorDatabaseConstant.URL_CONNECTOR_DATABASE_CONTROLLER_EVENT)
    public abstract void event(@RequestBody String event);
}
