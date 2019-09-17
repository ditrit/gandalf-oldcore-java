package com.ditrit.gandalf.modules.utility.jobmodule.core;

import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//TODO REVOIR GENERIC
@Configuration
public class JobConfiguration {

    @Value("${gandalf.job.broker}")
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
