package com.orness.gandalf.core.connector.connectorworkflowengineservice;

import com.orness.gandalf.core.connector.connectorworkflowengineservice.communication.command.WorkerWorkflowEngineZeroMQ;
import com.orness.gandalf.core.connector.connectorworkflowengineservice.config.TaskExecutorConfiguration;
import io.zeebe.client.ZeebeClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
@EnableDiscoveryClient
public class ConnectorWorkflowEngineServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConnectorWorkflowEngineServiceApplication.class, args);
	}

	@Bean
	public void workerWorkflowEngineCommand() {
		ApplicationContext context = new AnnotationConfigApplicationContext(TaskExecutorConfiguration.class);
		ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");

		WorkerWorkflowEngineZeroMQ workerWorkflowEngineZeroMQ = (WorkerWorkflowEngineZeroMQ) context.getBean("workerWorkflowEngineZeroMQ");
		taskExecutor.execute(workerWorkflowEngineZeroMQ);
	}

	@Bean
	public ZeebeClient zeebe() {
		//Client
		ZeebeClient zeebeClient = ZeebeClient.newClient();
		return zeebeClient;
	}
}
