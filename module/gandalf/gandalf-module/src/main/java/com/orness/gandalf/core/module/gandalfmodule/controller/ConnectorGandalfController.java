package com.orness.gandalf.core.module.gandalfmodule.controller;

import com.orness.gandalf.core.module.gandalfmodule.manager.ConnectorGandalfManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.orness.gandalf.core.module.gandalfmodule.constant.ConnectorGandalfConstant.*;

@RestController(value = URL_CONNECTOR_GANDALF_CONTROLLER)
public class ConnectorGandalfController {

    private ConnectorGandalfManager connectorGandalfManager;

    @Autowired
    public ConnectorGandalfController(ConnectorGandalfManager connectorGandalfManager) {
        this.connectorGandalfManager = connectorGandalfManager;
    }

    @RequestMapping(method = RequestMethod.POST, value = URL_CONNECTOR_GANDALF_CONTROLLER_COMMAND)
    public void command(@RequestBody String command)  {
        //TODO
    }

    @RequestMapping(method = RequestMethod.POST, value = URL_CONNECTOR_GANDALF_CONTROLLER_EVENT)
    public void event(@RequestBody String event)  {
        //TODO
    }

}
