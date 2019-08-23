package com.orness.gandalf.core.test.testworker;

import com.orness.gandalf.core.test.testzeromq.command.RoutingWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TestWorkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestWorkerApplication.class, args);
	}

	@Bean
	public RoutingWorker worker() {
		return null;
	}

}
