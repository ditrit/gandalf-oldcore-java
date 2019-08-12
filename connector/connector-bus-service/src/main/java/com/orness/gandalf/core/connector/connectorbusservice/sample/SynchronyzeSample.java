package com.orness.gandalf.core.connector.connectorbusservice.sample;

import com.orness.gandalf.core.module.kafkamodule.common.manager.KafkaCommonManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SynchronyzeSample implements CommandLineRunner {

    @Autowired
    private KafkaCommonManager kafkaCommonManager;

    @Override
    public void run(String... args) throws Exception {
        this.kafkaCommonManager.synchronizeToBus("test");
    }
}