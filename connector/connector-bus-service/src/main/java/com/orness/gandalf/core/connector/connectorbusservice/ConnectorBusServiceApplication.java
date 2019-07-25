package com.orness.gandalf.core.connector.connectorbusservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {"com.orness.gandalf.core.module.messagemodule.gandalf.domain", "com.orness.gandalf.core.module.zeromqmodule"})
//TODO REVOIR ALL
public class ConnectorBusServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectorBusServiceApplication.class, args);
	}
}
