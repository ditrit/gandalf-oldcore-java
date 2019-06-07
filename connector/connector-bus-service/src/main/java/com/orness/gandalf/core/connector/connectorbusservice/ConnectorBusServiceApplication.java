package com.orness.gandalf.core.connector.connectorbusservice;

import com.orness.gandalf.core.connector.connectorbusservice.proxy.ProxyBean;
import com.orness.gandalf.core.connector.connectorbusservice.proxy.ProxyConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {"com.orness.gandalf.core.module.messagemodule.domain", "com.orness.gandalf.core.module.zeromqmodule"})
public class ConnectorBusServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectorBusServiceApplication.class, args);
	}

	@Bean
	public void proxy() {
		ApplicationContext context = new AnnotationConfigApplicationContext(ProxyConfiguration.class);
		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");

		ProxyBean proxyBean = (ProxyBean) context.getBean("proxyBean");
		taskExecutor.execute(proxyBean);
	}

}
