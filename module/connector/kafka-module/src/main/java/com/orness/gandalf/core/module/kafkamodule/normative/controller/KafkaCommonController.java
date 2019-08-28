package com.orness.gandalf.core.module.kafkamodule.normative.controller;

import com.orness.gandalf.core.module.busmodule.controller.BusCommonController;
import com.orness.gandalf.core.module.kafkamodule.normative.manager.KafkaCommonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "commonController")
@Profile(value = "kafka-module")
public class KafkaCommonController extends BusCommonController {

    private KafkaCommonManager kafkaCommonManager;

    @Autowired
    public KafkaCommonController(KafkaCommonManager kafkaCommonManager) {
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
