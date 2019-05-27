package com.orness.gandalf.core.test.javakafka1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {"com.orness.gandalf.core.messagebusmodule.domain"})
public class JavaKafka1Application {

	public static void main(String[] args) {
		SpringApplication.run(JavaKafka1Application.class, args);
	}

}
