package com.orness.core.databasekafkaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {"com.orness.namt.messagekafkamodule.domain"})
@EnableJpaRepositories(basePackages = {"com.orness.namt.messagekafkamodule.repository"})
public class DatabaseKafkaServiceApplication {

	//TODO USE PROPERTIES
	public static void main(String[] args) {
		SpringApplication.run(DatabaseKafkaServiceApplication.class, args);
	}

}
