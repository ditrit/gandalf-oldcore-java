package com.orness.gandalf.core.service.gandalfdatabaseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {"com.orness.gandalf.core.module.messagemodule.gandalf.domain"})
@EnableJpaRepositories(basePackages = {"com.orness.gandalf.core.module.messagemodule.gandalf.repository"})
public class GandalfDatabaseServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GandalfDatabaseServiceApplication.class, args);
	}

}
