package com.ditrit.gandalf.services.buildservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class BuildServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BuildServiceApplication.class, args);
	}

}
