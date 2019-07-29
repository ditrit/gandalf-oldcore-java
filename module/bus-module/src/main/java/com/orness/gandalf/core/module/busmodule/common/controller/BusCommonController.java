package com.orness.gandalf.core.module.busmodule.common.controller;

public abstract class BusCommonController {

    public abstract void createTopic(String topic);

    public abstract void deleteTopic(String topic);

    public abstract void sendMessage(String topic, String message);

    public abstract void receiveMessage();

    public abstract void synchronizeTopicGandalf();

    public abstract void synchronizeBusTopicBus();

}
