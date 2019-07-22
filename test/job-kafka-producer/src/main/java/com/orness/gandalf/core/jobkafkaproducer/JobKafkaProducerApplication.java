package com.orness.gandalf.core.jobkafkaproducer;

import io.zeebe.client.ZeebeClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableDiscoveryClient
public class JobKafkaProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobKafkaProducerApplication.class, args);
	}

	@Bean
	public ZeebeClient zeebe() {
		//Client
		ZeebeClient zeebeClient = ZeebeClient.newClient();
		return zeebeClient;
	}
}
