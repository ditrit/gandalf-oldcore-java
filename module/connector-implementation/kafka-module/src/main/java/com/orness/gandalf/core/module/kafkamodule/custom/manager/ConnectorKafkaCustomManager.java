package com.orness.gandalf.core.module.kafkamodule.custom.manager;

import com.orness.gandalf.core.module.kafkamodule.properties.ConnectorKafkaProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component(value = "customManager")
@ConditionalOnBean(ConnectorKafkaProperties.class)
public class ConnectorKafkaCustomManager {
}
