package com.orness.gandalf.core.connector.databasebusservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {"com.orness.gandalf.core.module.messagebusmodule.domain"})
@EnableJpaRepositories(basePackages = {"com.orness.gandalf.core.module.messagebusmodule.repository"})
public class DatabaseBusServiceApplication {

	//TODO USE PROPERTIES
	public static void main(String[] args) {
		SpringApplication.run(DatabaseBusServiceApplication.class, args);
	}

}
