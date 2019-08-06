package com.orness.gandalf.core.module.kafkamodule.core.config;

import com.orness.gandalf.core.module.busmodule.core.properties.BusProperties;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Profile(value = "kafka-module")
public class KafkaConfiguration {

    private BusProperties busProperties;

    @Autowired
    public KafkaConfiguration(BusProperties busProperties) {
        this.busProperties = busProperties;
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, this.busProperties.getBus());
        return new KafkaAdmin(configs);
    }
}
