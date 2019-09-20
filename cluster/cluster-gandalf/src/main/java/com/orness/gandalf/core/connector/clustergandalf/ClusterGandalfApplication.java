package com.orness.gandalf.core.connector.clustergandalf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@SpringBootApplication
@EnableDiscoveryClient
//@EntityScan(basePackages = {"com.orness.gandalf.core.module.zeromqmodule"})
public class ClusterGandalfApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClusterGandalfApplication.class, args);
	}

}
