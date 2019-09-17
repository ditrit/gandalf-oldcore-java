package com.ditrit.gandalf.modules.utility.workflowuidmodule.repository;

import com.ditrit.gandalf.modules.utility.workflowuidmodule.domain.WorkflowUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface WorkflowUIDRepository  extends JpaRepository<WorkflowUID, Long> {

    WorkflowUID getWorkflowUidByName(String name);
}
