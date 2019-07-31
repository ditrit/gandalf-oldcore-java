package com.orness.gandalf.core.module.zeebemodule.core.config;

import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile(value = "zeebe-module")
public class ZeebeConfiguration {

    @Value("${gandalf.workflowengine.broker}")
    private String brokerAddress;

    @Bean
    public ZeebeClient zeebe() {
        ZeebeClient zeebeClient = ZeebeClient.newClientBuilder()
                .brokerContactPoint(brokerAddress)
                .build();
        return zeebeClient;
    }
}
