package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.normative.controller;

import com.ditrit.gandalf.modules.abstractconnectors.abstractbus.controller.ConnectorBusNormativeController;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.normative.manager.ConnectorKafkaNormativeManager;
import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.properties.ConnectorKafkaProperties;
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
