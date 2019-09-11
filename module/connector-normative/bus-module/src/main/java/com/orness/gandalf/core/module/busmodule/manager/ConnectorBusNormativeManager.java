package com.orness.gandalf.core.module.busmodule.manager;

import java.util.List;

public abstract class ConnectorBusNormativeManager {

    private List<String> synchronizeTopics;

    public List<String> getSynchronizeTopics() {
        return synchronizeTopics;
    }

    public void setSynchronizeTopics(List<String> synchronizeTopics) {
        this.synchronizeTopics = synchronizeTopics;
    }

    public abstract void createTopic(String payload);

    public abstract void deleteTopic(String payload);

    public abstract void sendMessage(String payload);

    public abstract String receiveMessage(String payload);

    public abstract void addSynchronizeTopicToGandalf(String payload);

    public abstract void addSynchronizeTopicToBus(String payload);

    public abstract void synchronizeToGandalf(String topic, String event, String payload);

    public abstract void synchronizeToBus(String topic, String event, String payload);

}
