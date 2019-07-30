package com.orness.gandalf.core.module.busmodule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


//TODO REVOIR AGRS
@RequestMapping("/bus")
public abstract class BusCommonController {

    @RequestMapping(method = RequestMethod.GET, value = "/topic/create/{topic}")
    public abstract void createTopic(String topic);

    @RequestMapping(method = RequestMethod.GET, value = "/topic/delete/{topic}")
    public abstract void deleteTopic(String topic);

    @RequestMapping(method = RequestMethod.POST, value = "/message/send/{topic}")
    public abstract void sendMessage(String topic, String message);

    @RequestMapping(method = RequestMethod.GET, value = "/message/receive/{topic}")
    public abstract void receiveMessage();

    @RequestMapping(method = RequestMethod.GET, value = "/synchronize/gandalf/{topic}")
    public abstract void synchronizeGandalf();

    @RequestMapping(method = RequestMethod.GET, value = "/synchronize/bus/{topic}")
    public abstract void synchronizeBus();

}
