package com.orness.gandalf.core.connector.connectorworkflowengineservice;

import io.zeebe.client.ZeebeClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class ConnectorWorkflowEngineServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectorWorkflowEngineServiceApplication.class, args);
	}

	@Bean
	public ZeebeClient zeebe() {
		//Client
		ZeebeClient zeebeClient = ZeebeClient.newClient();
		return zeebeClient;
	}
}
