package com.orness.core.workflowuidservice.service;

import com.orness.core.workflowuidmodule.domain.WorkflowUID;
import com.orness.core.workflowuidmodule.repository.WorkflowUIDRepository;
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
