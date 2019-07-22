package com.orness.gandalf.core.connector.connectorversioncontrolservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@SpringBootApplication
public class ConnectorVersionControlServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectorVersionControlServiceApplication.class, args);
	}

}
