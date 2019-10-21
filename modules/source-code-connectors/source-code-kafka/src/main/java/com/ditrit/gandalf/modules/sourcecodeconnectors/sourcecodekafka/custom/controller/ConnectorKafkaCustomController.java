package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.custom.controller;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.properties.ConnectorKafkaProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "customController")
@ConditionalOnBean(ConnectorKafkaProperties.class)
public class ConnectorKafkaCustomController {
}
