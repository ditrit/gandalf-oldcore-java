package com.orness.gandalf.core.module.customorchestratormodule.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile(value = "custom-orchestrator-module")
@EnableConfigurationProperties
@ConfigurationProperties(prefix="customorchestrator")
public class ConnectorCustomOrchestratorProperties {

    private String worker;

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }
}
