package com.orness.gandalf.core.module.busmodule.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static com.orness.gandalf.core.module.busmodule.constant.BusConstant.*;


//TODO REVOIR AGRS
@RequestMapping(value = URL_CONTROLLER)
public abstract class BusCommonController {

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_CREATE_TOPIC)
    public abstract void createTopic(String topic);

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_DELETE_TOPIC)
    public abstract void deleteTopic(String topic);

    @RequestMapping(method = RequestMethod.POST, value = URL_CONTROLLER_SEND_MESSAGE)
    public abstract void sendMessage(String topic, String message);

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_RECEIVE_MESSAGE)
    public abstract void receiveMessage();

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_SYNCHRONIZE_GANDALF)
    public abstract void synchronizeGandalf();

    @RequestMapping(method = RequestMethod.GET, value = URL_CONTROLLER_SYNCHRONIZE_BUS)
    public abstract void synchronizeBus();

}
