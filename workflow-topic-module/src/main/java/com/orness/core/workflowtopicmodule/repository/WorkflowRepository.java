package com.orness.core.workflowtopicmodule.repository;

import com.orness.core.workflowtopicmodule.domain.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface WorkflowRepository extends JpaRepository<Workflow, Long> {
}
