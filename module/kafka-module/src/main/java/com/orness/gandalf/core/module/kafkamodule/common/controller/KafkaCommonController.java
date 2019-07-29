package com.orness.gandalf.core.module.kafkamodule.common.controller;

import com.orness.gandalf.core.module.busmodule.common.controller.BusCommonController;
import com.orness.gandalf.core.module.kafkamodule.common.manager.KafkaCommonManager;
import org.springframework.beans.factory.annotation.Autowired;

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
    public void receiveMessage() {
        this.kafkaCommonManager.receiveMessage();
    }

    @Override
    public void synchronizeTopicGandalf() {
        this.kafkaCommonManager.synchronizeTopicGandalf();
    }

    @Override
    public void synchronizeBusTopicBus() {
        this.kafkaCommonManager.synchronizeBusTopicBus();
    }
}
