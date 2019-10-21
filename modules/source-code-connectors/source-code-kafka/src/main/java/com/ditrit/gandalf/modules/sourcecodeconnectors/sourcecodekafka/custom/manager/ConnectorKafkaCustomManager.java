package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.custom.manager;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.properties.ConnectorKafkaProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

@Component(value = "customManager")
@ConditionalOnBean(ConnectorKafkaProperties.class)
public class ConnectorKafkaCustomManager {
}
