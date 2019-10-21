package com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.core.config;

import com.ditrit.gandalf.modules.sourcecodeconnectors.sourcecodekafka.properties.ConnectorKafkaProperties;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnBean(ConnectorKafkaProperties.class)
public class KafkaConfiguration {

    private ConnectorKafkaProperties connectorKafkaProperties;

    @Autowired
    public KafkaConfiguration(ConnectorKafkaProperties connectorKafkaProperties) {
        this.connectorKafkaProperties = connectorKafkaProperties;
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, this.connectorKafkaProperties.getEndPointConnection());
        return new KafkaAdmin(configs);
    }
}
