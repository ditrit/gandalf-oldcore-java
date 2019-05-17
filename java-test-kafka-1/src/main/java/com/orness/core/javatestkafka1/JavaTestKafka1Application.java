package com.orness.core.javatestkafka1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {"com.orness.namt.messagebusmodule.domain"})
public class JavaTestKafka1Application {

	public static void main(String[] args) {
		SpringApplication.run(JavaTestKafka1Application.class, args);
	}

}
