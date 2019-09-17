package com.ditrit.gandalf.connectors.connectorartifact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableDiscoveryClient
public class ConnectorArtifactApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectorArtifactApplication.class, args);
	}

}
