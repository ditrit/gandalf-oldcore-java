package com.orness.gandalf.core.service.databasebusservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {"com.orness.gandalf.core.module.messagemodule.domain"})
@EnableJpaRepositories(basePackages = {"com.orness.gandalf.core.module.messagemodule.repository"})
public class DatabaseBusServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseBusServiceApplication.class, args);
	}

}