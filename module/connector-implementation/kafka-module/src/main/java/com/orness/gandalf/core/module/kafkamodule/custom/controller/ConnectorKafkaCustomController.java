package com.orness.gandalf.core.module.kafkamodule.custom.controller;


import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "customController")
@Profile(value = "kafka")
public class ConnectorKafkaCustomController {
}
