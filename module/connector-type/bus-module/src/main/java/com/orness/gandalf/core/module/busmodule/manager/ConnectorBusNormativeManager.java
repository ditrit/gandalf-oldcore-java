package com.orness.gandalf.core.module.busmodule.manager;

public abstract class ConnectorBusNormativeManager {

    public abstract void createTopic(String payload);

    public abstract void deleteTopic(String payload);

    public abstract void sendMessage(String payload);

    public abstract String receiveMessage(String payload);

    public abstract void synchronizeToGandalf(String payload);

    public abstract void synchronizeToBus(String payload);
}
