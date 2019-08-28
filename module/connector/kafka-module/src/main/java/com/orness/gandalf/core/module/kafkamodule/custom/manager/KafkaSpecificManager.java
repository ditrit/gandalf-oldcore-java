package com.orness.gandalf.core.module.kafkamodule.custom.manager;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component(value = "specificManager")
@Profile(value = "kafka-module")
public class KafkaSpecificManager {
}
