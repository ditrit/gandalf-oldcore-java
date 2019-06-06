package com.orness.gandalf.core.connector.connectorbusservice;

import com.orness.gandalf.core.module.zeromqmodule.proxy.PubSubProxyZeroMQ;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {"com.orness.gandalf.core.module.messagemodule.domain", "com.orness.gandalf.core.module.zeromqmodule"})
@ComponentScan(basePackages = {"com.orness.gandalf.core.module.zeromqmodule"})
public class ConnectorBusServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectorBusServiceApplication.class, args);
	}

	@Bean
	public PubSubProxyZeroMQ proxy() {
		//PubSubProxyZeroMQ pubSubProxyZeroMQ = new PubSubProxyZeroMQ("ipc://sub", "ipc://pub");
		return new PubSubProxyZeroMQ("ipc://sub", "ipc://pub");
	}
}
