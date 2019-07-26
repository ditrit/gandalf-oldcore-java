package com.orness.gandalf.core.module.busmodule.common.manager;

public abstract class BusCommonManager {

    public abstract void createTopic(String topic);

    public abstract void deleteTopic(String topic);

    public abstract void sendMessage(String topic, String message);

    public abstract void receiveMessage();

    public abstract void synchronizeTopicGandalf();

    public abstract void synchronizeBusTopicBus();
}
