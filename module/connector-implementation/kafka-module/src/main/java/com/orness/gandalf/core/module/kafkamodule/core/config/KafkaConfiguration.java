package com.orness.gandalf.core.module.kafkamodule.core.config;

import com.orness.gandalf.core.module.busmodule.properties.ConnectorBusProperties;
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

    private ConnectorBusProperties connectorBusProperties;

    @Autowired
    public KafkaConfiguration(ConnectorBusProperties connectorBusProperties) {
        this.connectorBusProperties = connectorBusProperties;
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, this.connectorBusProperties.getBusConnection());
        return new KafkaAdmin(configs);
    }
}
