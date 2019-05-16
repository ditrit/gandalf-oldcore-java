package com.orness.core.workflowuidservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.orness.namt.workflowuidmodule.domain"})
@EnableJpaRepositories(basePackages = {"com.orness.namt.workflowuidmodule.repository"})
public class WorkflowUidServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkflowUidServiceApplication.class, args);
	}

}
