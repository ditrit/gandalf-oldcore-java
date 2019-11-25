package com.ditrit.gandalf.gandalfjava.services.workflowservice.config;

import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkflowServiceConfiguration {

    @Value("${gandalf.workflow.broker}")
    private String brokerAddress;

    @Bean
    public ZeebeClient zeebe() {
        //Client
        ZeebeClient zeebeClient = ZeebeClient.newClientBuilder()
                .brokerContactPoint(brokerAddress)
                .build();
        return zeebeClient;
    }
}
