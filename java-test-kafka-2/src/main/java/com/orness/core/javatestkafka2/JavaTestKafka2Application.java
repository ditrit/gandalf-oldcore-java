package com.orness.core.javatestkafka2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {"com.orness.namt.messagekafkamodule.domain"})
public class JavaTestKafka2Application {

	public static void main(String[] args) {
		SpringApplication.run(JavaTestKafka2Application.class, args);
	}

}
