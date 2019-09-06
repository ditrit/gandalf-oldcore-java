package com.orness.gandalf.core.module.kafkamodule.custom.manager;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "customManager")
@Profile(value = "kafka")
public class ConnectorKafkaCustomManager {
}
