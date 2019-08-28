package com.orness.gandalf.core.module.kafkamodule.normative.controller;

import com.orness.gandalf.core.module.busmodule.controller.ConnectorBusNormativeController;
import com.orness.gandalf.core.module.kafkamodule.normative.manager.ConnectorKafkaNormativeManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "commonController")
@Profile(value = "kafka-module")
public class ConnectorKafkaNormativeController extends ConnectorBusNormativeController {

    private ConnectorKafkaNormativeManager kafkaCommonManager;

    @Autowired
    public ConnectorKafkaNormativeController(ConnectorKafkaNormativeManager kafkaCommonManager) {
        this.kafkaCommonManager = kafkaCommonManager;
    }

    @Override
    public void createTopic(String topic) {
        this.kafkaCommonManager.createTopic(topic);
    }

    @Override
    public void deleteTopic(String topic) {
        this.kafkaCommonManager.deleteTopic(topic);
    }

    @Override
    public void sendMessage(String topic, String message) {
        this.kafkaCommonManager.sendMessage(topic, message);
    }

    @Override
    public String receiveMessage(String topic) {
        return this.kafkaCommonManager.receiveMessage(topic);
    }

    @Override
    public void synchronizeToGandalf(String topic) {
        this.kafkaCommonManager.synchronizeToGandalf("");
    }

    @Override
    public void synchronizeToBus(String topic) {
        this.kafkaCommonManager.synchronizeToBus("");
    }
}
