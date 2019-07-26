package com.orness.gandalf.core.module.zeebemodule.core.config;

import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
