package com.ditrit.gandalf.services.workflowuidservice.controller;

import com.ditrit.gandalf.modules.utility.workflowuidmodule.domain.WorkflowUID;
import com.ditrit.gandalf.services.workflowuidservice.service.WorkflowUIDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("workflow-uid")
public class WorkflowUIDRestController {

    private static WorkflowUIDService workflowUIDService;

    @Autowired
    public WorkflowUIDRestController() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        workflowUIDService = (WorkflowUIDService) context.getBean("WorkflowUIDService");
    }

    @GetMapping(name = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public WorkflowUID getWorkflowUIDByName(@PathVariable String name) {
        return workflowUIDService.getWorkflowUIDByName(name);
    }

    @PostMapping(name = "/new", produces = MediaType.APPLICATION_JSON_VALUE)
    public WorkflowUID getBook(@RequestBody  WorkflowUID workflowUID) {
        return workflowUIDService.saveNewWorkflowIUD(workflowUID);
    }
}
