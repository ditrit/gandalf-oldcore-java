package com.orness.gandalf.core.module.kafkamodule.normative.controller;

import com.orness.gandalf.core.module.busmodule.controller.ConnectorBusNormativeController;
import com.orness.gandalf.core.module.kafkamodule.normative.manager.ConnectorKafkaNormativeManager;
import com.orness.gandalf.core.module.kafkamodule.properties.ConnectorKafkaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "normativeController")
@ConditionalOnBean(ConnectorKafkaProperties.class)
public class ConnectorKafkaNormativeController extends ConnectorBusNormativeController {

    private ConnectorKafkaNormativeManager connectorKafkaNormativeManager;

    @Autowired
    public ConnectorKafkaNormativeController(ConnectorKafkaNormativeManager connectorKafkaNormativeManager) {
        this.connectorKafkaNormativeManager = connectorKafkaNormativeManager;
    }

    @Override
    public void command(String command) {

    }

    @Override
    public void event(String event) {

    }
}
