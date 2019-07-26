package com.orness.gandalf.core.connector.connectorgandalfservice.gandalf.controller;

import com.orness.gandalf.core.module.gandalfmodule.manager.GandalfConnectorManager;
import com.orness.gandalf.core.module.messagemodule.gandalf.domain.GandalfEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//TODO FINISH
@RestController
public class ConnectorGandalfController {

    private GandalfConnectorManager gandalfConnectorManager;

    @Autowired
    public ConnectorGandalfController(GandalfConnectorManager gandalfConnectorManager) {
        this.gandalfConnectorManager = gandalfConnectorManager;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/gandalf/start")
    public void start()  {
        this.gandalfConnectorManager.start();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/gandalf/stop")
    public void stop()  {
        this.gandalfConnectorManager.stop();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/gandalf/subscribe")
    public void subscribe()  {
        this.gandalfConnectorManager.subscribe();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/gandalf/unsubscribe")
    public void unsubscribe()  {
        this.gandalfConnectorManager.unsubscribe();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/gandalf/publish/{event}")
    public void publish(@RequestBody GandalfEvent gandalf)  {
        this.gandalfConnectorManager.publish(gandalf);
    }
}
