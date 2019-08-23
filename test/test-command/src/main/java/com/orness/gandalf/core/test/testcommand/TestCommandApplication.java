package com.orness.gandalf.core.test.testcommand;

import com.orness.gandalf.core.test.testzeromq.command.Worker;
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
public class TestCommandApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestCommandApplication.class, args);
	}

	@Bean
	public Worker command() {
		return null;
	}

}
