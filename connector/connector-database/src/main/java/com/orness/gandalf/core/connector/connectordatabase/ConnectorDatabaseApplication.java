package com.orness.gandalf.core.connector.connectordatabase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableDiscoveryClient
public class ConnectorDatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectorDatabaseApplication.class, args);
	}

}
