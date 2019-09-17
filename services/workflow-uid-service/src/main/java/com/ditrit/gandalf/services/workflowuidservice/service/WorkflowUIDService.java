package com.ditrit.gandalf.services.workflowuidservice.service;

import com.ditrit.gandalf.modules.utility.workflowuidmodule.domain.WorkflowUID;
import com.ditrit.gandalf.modules.utility.workflowuidmodule.repository.WorkflowUIDRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("WorkflowUIDService")
public class WorkflowUIDService {

    private WorkflowUIDRepository workflowUIDRepository;

    @Autowired
    public WorkflowUIDService(WorkflowUIDRepository workflowUIDRepository) {
        this.workflowUIDRepository = workflowUIDRepository;
    }

    public WorkflowUID getWorkflowUIDByName(String name) {
        return workflowUIDRepository.getWorkflowUidByName(name);
    }

    public WorkflowUID saveNewWorkflowIUD(WorkflowUID workflowUID) {
        return workflowUIDRepository.save(workflowUID);
    }
}
