package com.orness.gandalf.core.module.kafkamodule.core.config;

import com.orness.gandalf.core.module.busmodule.properties.ConnectorBusProperties;
import com.orness.gandalf.core.module.kafkamodule.properties.ConnectorKafkaProperties;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile(value = "kafka")
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
