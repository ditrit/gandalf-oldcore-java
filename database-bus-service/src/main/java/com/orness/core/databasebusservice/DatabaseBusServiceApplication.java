package com.orness.core.databasebusservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {"com.orness.namt.messagebusmodule.domain"})
@EnableJpaRepositories(basePackages = {"com.orness.namt.messagebusmodule.repository"})
public class DatabaseBusServiceApplication {

	//TODO USE PROPERTIES
	public static void main(String[] args) {
		SpringApplication.run(DatabaseBusServiceApplication.class, args);
	}

}
