package com.orness.core.connectorbusservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {"com.orness.core.messagebusmodule.domain"})
public class ConnectorBusServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectorBusServiceApplication.class, args);
	}
}
