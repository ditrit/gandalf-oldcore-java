package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.standard.controller;

import com.ditrit.gandalf.modules.abstractconnectors.abstractbus.controller.ConnectorBusStandardController;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.standard.manager.ConnectorKafkaStandardManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.properties.ConnectorKafkaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "standardController")
@ConditionalOnBean(ConnectorKafkaProperties.class)
public class ConnectorKafkaStandardController extends ConnectorBusStandardController {

    private ConnectorKafkaStandardManager connectorKafkaStandardManager;

    @Autowired
    public ConnectorKafkaStandardController(ConnectorKafkaStandardManager connectorKafkaStandardManager) {
        this.connectorKafkaStandardManager = connectorKafkaStandardManager;
    }

    @Override
    public void command(String command) {

    }

    @Override
    public void event(String event) {

    }
}
