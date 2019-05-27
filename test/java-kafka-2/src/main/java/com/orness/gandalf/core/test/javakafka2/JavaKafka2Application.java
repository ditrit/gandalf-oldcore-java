package com.orness.gandalf.core.test.javakafka2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {"com.orness.gandalf.core.messagebusmodule.domain"})
public class JavaKafka2Application {

	public static void main(String[] args) {
		SpringApplication.run(JavaKafka2Application.class, args);
	}

}
