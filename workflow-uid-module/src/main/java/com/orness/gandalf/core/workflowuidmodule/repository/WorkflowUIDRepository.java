package com.orness.gandalf.core.workflowuidmodule.repository;

import com.orness.gandalf.core.workflowuidmodule.domain.WorkflowUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface WorkflowUIDRepository  extends JpaRepository<WorkflowUID, Long> {

    WorkflowUID getWorkflowUidByName(String name);
}
