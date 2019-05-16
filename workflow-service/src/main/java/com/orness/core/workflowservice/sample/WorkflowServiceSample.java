package com.orness.core.workflowservice.sample;

import com.orness.core.workflowservice.domain.WorkflowManager;
import io.zeebe.client.api.events.DeploymentEvent;
import io.zeebe.client.api.events.WorkflowInstanceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WorkflowServiceSample implements CommandLineRunner {

    private WorkflowManager workflowManager;

    @Autowired
    public WorkflowServiceSample(WorkflowManager workflowManager) {
        this.workflowManager = workflowManager;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start sample");

        DeploymentEvent deploymentEvent;
        WorkflowInstanceEvent workflowInstanceEvent;
        //WORKFLOW WORKFLOW 2 WORKFLOW
        String name = "diagram_kafka_producer.bpmn";
        deploymentEvent = workflowManager.deployWorkflow(name);
        String id = workflowManager.getIdDeployment(deploymentEvent);
        System.out.println(id);
        workflowInstanceEvent = workflowManager.InstanceWorkflow(id, name, name, "START", "zeebeW2W");

        //WORKFLOW WORKFLOW 2 JAVA
        name = "diagram_kafka_print_continue.bpmn";
        deploymentEvent = workflowManager.deployWorkflow(name);
        id = workflowManager.getIdDeployment(deploymentEvent);
        System.out.println(id);
        workflowInstanceEvent =workflowManager.InstanceWorkflow(id, name, name, "zeebeW2W", "zeebeW2J");

        //WORKFLOW JAVA 2 WORKFLOW
        name = "diagram_kafka_print.bpmn";
        deploymentEvent = workflowManager.deployWorkflow(name);
        id = workflowManager.getIdDeployment(deploymentEvent);
        System.out.println(id);
        workflowInstanceEvent = workflowManager.InstanceWorkflow(id, name, name, "zeebeJ2W", "END");
    }
}
