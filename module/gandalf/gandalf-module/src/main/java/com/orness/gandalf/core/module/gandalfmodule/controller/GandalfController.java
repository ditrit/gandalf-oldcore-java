package com.orness.gandalf.core.module.gandalfmodule.controller;

import com.orness.gandalf.core.module.gandalfmodule.manager.GandalfConnectorManager;
import com.orness.gandalf.core.module.messagemodule.gandalf.domain.GandalfEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.orness.gandalf.core.module.gandalfmodule.constant.GandalfConstant.*;

@RestController(value = URL_CONTROLLER)
public class GandalfController {

    private GandalfConnectorManager gandalfConnectorManager;

    @Autowired
    public GandalfController(GandalfConnectorManager gandalfConnectorManager) {
        this.gandalfConnectorManager = gandalfConnectorManager;
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_START)
    public void start()  {
        this.gandalfConnectorManager.start();
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_STOP)
    public void stop()  {
        this.gandalfConnectorManager.stop();
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_SUBSCRIBE)
    public void subscribe(String topic)  {
        this.gandalfConnectorManager.subscribe(topic);
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_UNSUBSCRIBE)
    public void unsubscribe(String topic)  {
        this.gandalfConnectorManager.unsubscribe(topic);
    }

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_PUBLISH)
    public void publish(@RequestBody GandalfEvent gandalf)  {
        this.gandalfConnectorManager.publish(gandalf);
    }
}
