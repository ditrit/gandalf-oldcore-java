package com.ditrit.gandalf.connectors.connectorbus.sample;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.standard.manager.ConnectorKafkaStandardManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SynchronyzeSample implements CommandLineRunner {

    @Autowired
    private ConnectorKafkaStandardManager kafkaCommonManager;

    @Override
    public void run(String... args) throws Exception {
        this.kafkaCommonManager.addSynchronizeTopicToBus("test.test");
    }
}
