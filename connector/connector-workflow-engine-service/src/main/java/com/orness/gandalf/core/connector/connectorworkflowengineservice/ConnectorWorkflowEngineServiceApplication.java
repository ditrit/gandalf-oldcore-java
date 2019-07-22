package com.orness.gandalf.core.connector.connectorworkflowengineservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableDiscoveryClient
public class ConnectorWorkflowEngineServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectorWorkflowEngineServiceApplication.class, args);
	}

}
