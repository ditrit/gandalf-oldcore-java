package com.orness.gandalf.core.service.communicationservice;

import com.orness.gandalf.core.service.communicationservice.command.ProxyCommandBean;
import com.orness.gandalf.core.service.communicationservice.config.ProxyConfiguration;
import com.orness.gandalf.core.service.communicationservice.event.ProxyEventBean;
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
@EntityScan(basePackages = {"com.orness.gandalf.core.module.zeromqmodule"})
public class CommunicationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunicationServiceApplication.class, args);
	}

	@Bean
	public void proxyCommand() {
		ApplicationContext context = new AnnotationConfigApplicationContext(ProxyConfiguration.class);
		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");

		ProxyCommandBean proxyCommandBean = (ProxyCommandBean) context.getBean("proxyCommandBean");
		taskExecutor.execute(proxyCommandBean);
	}

	@Bean
	public void proxyEvent() {
		ApplicationContext context = new AnnotationConfigApplicationContext(ProxyConfiguration.class);
		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");

		ProxyEventBean proxyEventBean = (ProxyEventBean) context.getBean("proxyEventBean");
		taskExecutor.execute(proxyEventBean);
	}

}
