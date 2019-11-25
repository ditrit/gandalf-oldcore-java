package com.ditrit.gandalf.gandalfjava.connectors.connectorversioncontrol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@SpringBootApplication
public class ConnectorVersionControlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectorVersionControlApplication.class, args);
	}

}
