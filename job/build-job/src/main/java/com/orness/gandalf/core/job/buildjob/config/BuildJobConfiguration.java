package com.orness.gandalf.core.job.buildjob.config;

import com.orness.gandalf.core.job.buildjob.properties.BuildJobProperties;
import io.zeebe.client.ZeebeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BuildJobConfiguration {

    private BuildJobProperties buildJobProperties;

    @Autowired
    public BuildJobConfiguration(BuildJobProperties buildJobProperties) {
        this.buildJobProperties = buildJobProperties;
    }

    @Bean
    public ZeebeClient zeebe() {
        //Client
        ZeebeClient zeebeClient = ZeebeClient.newClientBuilder()
                .brokerContactPoint(this.buildJobProperties.getEndPointConnection())
                .build();
        return zeebeClient;
    }
}
