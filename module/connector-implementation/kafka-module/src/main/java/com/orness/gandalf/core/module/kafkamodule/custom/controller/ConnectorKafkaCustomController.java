package com.orness.gandalf.core.module.kafkamodule.custom.controller;

import com.orness.gandalf.core.module.kafkamodule.properties.ConnectorKafkaProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "customController")
@ConditionalOnBean(ConnectorKafkaProperties.class)
public class ConnectorKafkaCustomController {
}
