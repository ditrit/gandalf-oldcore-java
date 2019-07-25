package com.orness.gandalf.core.connector.connectordatabaseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableDiscoveryClient
public class ConnectorDatabaseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectorDatabaseServiceApplication.class, args);
	}

}
