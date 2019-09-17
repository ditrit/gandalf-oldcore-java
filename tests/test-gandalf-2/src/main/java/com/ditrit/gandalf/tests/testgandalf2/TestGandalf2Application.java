package com.ditrit.gandalf.tests.testgandalf2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableDiscoveryClient
public class TestGandalf2Application {

	public static void main(String[] args) {
		SpringApplication.run(TestGandalf2Application.class, args);
	}

}
