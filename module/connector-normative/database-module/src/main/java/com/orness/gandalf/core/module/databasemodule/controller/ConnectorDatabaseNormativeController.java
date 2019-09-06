package com.orness.gandalf.core.module.databasemodule.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import static com.orness.gandalf.core.module.databasemodule.constant.ConnectorDatabaseConstant.*;

@RequestMapping(value = URL_CONNECTOR_DATABASE_CONTROLLER)
public abstract class ConnectorDatabaseNormativeController {

    @RequestMapping(method = RequestMethod.POST, value = URL_CONNECTOR_DATABASE_CONTROLLER_COMMAND)
    public abstract void command(@RequestBody String command);

    @RequestMapping(method = RequestMethod.POST, value = URL_CONNECTOR_DATABASE_CONTROLLER_EVENT)
    public abstract void event(@RequestBody String event);
}
