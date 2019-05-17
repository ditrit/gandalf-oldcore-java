package com.orness.core.connectorworkflowengineservice;

import io.zeebe.client.ZeebeClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
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
