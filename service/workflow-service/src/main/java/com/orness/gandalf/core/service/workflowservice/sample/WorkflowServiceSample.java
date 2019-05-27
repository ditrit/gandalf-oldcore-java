package com.orness.gandalf.core.service.workflowservice.sample;

import com.orness.gandalf.core.library.grpcjavaclient.workflowengine.GrpcWorkflowEngineJavaClient;
import com.orness.gandalf.core.service.workflowservice.manager.WorkflowManager;
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
        workflowInstanceEvent = workflowManager.InstanceWorkflow(id, name, "content","","zeebeW2W");

        //WORKFLOW WORKFLOW 2 JAVA
        name = "diagram_kafka_print_continue.bpmn";
        deploymentEvent = workflowManager.deployWorkflow(name);
        id = workflowManager.getIdDeployment(deploymentEvent);
        System.out.println(id);
        workflowInstanceEvent = workflowManager.InstanceWorkflow(id, name, "content","zeebeW2W","zeebeW2J");
        GrpcWorkflowEngineJavaClient grpcWorkflowEngineJavaClientW2W = new GrpcWorkflowEngineJavaClient();
        grpcWorkflowEngineJavaClientW2W.subscribeTopic("zeebeW2W", name);

        //WORKFLOW JAVA 2 WORKFLOW
        name = "diagram_kafka_print.bpmn";
        deploymentEvent = workflowManager.deployWorkflow(name);
        id = workflowManager.getIdDeployment(deploymentEvent);
        System.out.println(id);
        workflowInstanceEvent = workflowManager.InstanceWorkflow(id, name,"content","zeebeJ2W","");
        GrpcWorkflowEngineJavaClient grpcWorkflowEngineJavaClientJ2W = new GrpcWorkflowEngineJavaClient();
        grpcWorkflowEngineJavaClientJ2W.subscribeTopic("zeebeJ2W", name);
    }
}
