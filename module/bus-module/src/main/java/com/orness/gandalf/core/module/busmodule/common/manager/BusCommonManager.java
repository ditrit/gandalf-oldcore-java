package com.orness.gandalf.core.module.busmodule.common.manager;

public abstract class BusCommonManager {

    public abstract void createTopic();

    public abstract void deleteTopic();

    public abstract void sendTopic();

    public abstract void receiveTopic();

    public abstract void synchronizeTopicGandalf();

    public abstract void synchronizeBusTopicBus();
}
