package com.orness.gandalf.core.module.busmodule.manager;

public abstract class ConnectorBusNormativeManager {

    public abstract void createTopic(String topic);

    public abstract void deleteTopic(String topic);

    public abstract void sendMessage(String topic, String message);

    public abstract String receiveMessage(String topic);

    public abstract void synchronizeToGandalf(String topic);

    public abstract void synchronizeToBus(String topic);
}
