package com.orness.gandalf.core.service.workflowuidservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.orness.gandalf.core.workflowuidmodule.manager"})
@EnableJpaRepositories(basePackages = {"com.orness.gandalf.core.workflowuidmodule.repository"})
public class WorkflowUidServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkflowUidServiceApplication.class, args);
	}

}
