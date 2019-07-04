package com.orness.gandalf.core.service.workflowservice.sample;

import com.orness.gandalf.core.library.zeromqjavaclient.ZeroMQJavaClient;
import com.orness.gandalf.core.service.workflowservice.manager.WorkflowManager;
import io.zeebe.client.api.events.DeploymentEvent;
import io.zeebe.client.api.events.WorkflowInstanceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class WorkflowServiceSample implements CommandLineRunner {


    @Value("${gandalf.communication.client}")
    private String connectionWorker;

    @Value("${gandalf.communication.subscriber}")
    private String connectionSubscriber;

    private WorkflowManager workflowManager;

    @Autowired
    public WorkflowServiceSample(WorkflowManager workflowManager) {
        this.workflowManager = workflowManager;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start sample");
        //testPerformanceLoop(100, false);
        demoPresentation();
    }

    public void demoPresentation() {
        DeploymentEvent deploymentEvent;
        WorkflowInstanceEvent workflowInstanceEvent;
        ZeroMQJavaClient zeroMQJavaClient = new ZeroMQJavaClient(connectionWorker, connectionSubscriber);
        //CREATE TOPIC
        zeroMQJavaClient.createTopic("webhook");

        //SUB TOPIC
        zeroMQJavaClient.subscribeWorkflowEngineTopic("webhook");

        //WORKFLOW
        String name = "diagram_demo.bpmn";
        deploymentEvent = workflowManager.deployWorkflow(name);
        String id = workflowManager.getIdDeployment(deploymentEvent);
        System.out.println(id);
        String instance_name_continue = name;
        String instance_content = "content_" + instance_name_continue;
        workflowInstanceEvent = workflowManager.InstanceWorkflow(id, instance_name_continue,instance_content, "webhook","");


    }

    public void testJob() {
        DeploymentEvent deploymentEvent;
        WorkflowInstanceEvent workflowInstanceEvent;
        //ZeroMQJavaClient zeroMQJavaClient = new ZeroMQJavaClient(connectionWorker, connectionSubscriber);

        //WORKFLOW
        String name = "diagram_1.bpmn";
        deploymentEvent = workflowManager.deployWorkflow(name);
        String id = workflowManager.getIdDeployment(deploymentEvent);
        System.out.println(id);
        String instance_name_continue = name;
        String instance_content = "content_" + instance_name_continue;
        workflowInstanceEvent = workflowManager.InstanceWorkflow(id, instance_name_continue,instance_content, "job","job");
    }

    public void testPerformanceLoop(int numberIteration, boolean multipleTopic) {
        for(int indice = 0; indice < numberIteration; indice++) {
            testPerformance(indice, multipleTopic);
        }
    }


    public void testPerformance(int indice, boolean multipleTopic) {
        DeploymentEvent deploymentEvent;
        WorkflowInstanceEvent workflowInstanceEvent;
        ZeroMQJavaClient zeroMQJavaClient = new ZeroMQJavaClient(connectionWorker, connectionSubscriber);

        //WORKFLOW JAVA 2 WORKFLOW
        String name = "diagram_kafka_print.bpmn";
        deploymentEvent = workflowManager.deployWorkflow(name);
        String id = workflowManager.getIdDeployment(deploymentEvent);
        System.out.println(id);
        String instance_name_continue = name+"_"+indice;
        String instance_content = "content_" + instance_name_continue + "_" + indice;
        workflowInstanceEvent = workflowManager.InstanceWorkflow(id, instance_name_continue,instance_content, topicMultiple("zeebeJ2W", multipleTopic, indice),"");
        System.out.println("TOTO");
        zeroMQJavaClient.subscribeWorkflowEngineTopic("zeebeJ2W");
        System.out.println("TATA");
        //grpcWorkflowEngineJavaClientJ2W.subscribeTopicWorkflow("zeebeJ2W", instance_name_continue);

        //WORKFLOW WORKFLOW 2 JAVA
        name = "diagram_kafka_print_continue.bpmn";
        deploymentEvent = workflowManager.deployWorkflow(name);
        id = workflowManager.getIdDeployment(deploymentEvent);
        System.out.println(id);
        instance_name_continue = name+"_"+indice;
        instance_content = "content_" + instance_name_continue + "_" + indice;
        workflowInstanceEvent = workflowManager.InstanceWorkflow(id, instance_name_continue, instance_content,topicMultiple("zeebeW2W", multipleTopic, indice), topicMultiple("zeebeW2J", multipleTopic, indice));
        zeroMQJavaClient.subscribeWorkflowEngineTopic("zeebeW2W");
        //grpcWorkflowEngineJavaClientW2W.subscribeTopicWorkflow("zeebeW2W", instance_name_continue);

        //WORKFLOW WORKFLOW 2 WORKFLOW
        name = "diagram_kafka_producer.bpmn";
        instance_name_continue = name+"_"+indice;
        instance_content = "content_" + instance_name_continue + "_" + indice;
        deploymentEvent = workflowManager.deployWorkflow(name);
        id = workflowManager.getIdDeployment(deploymentEvent);
        System.out.println(id);
        workflowInstanceEvent = workflowManager.InstanceWorkflow(id, instance_name_continue, instance_content,"", topicMultiple("zeebeW2W", multipleTopic, indice));




    }

    private String topicMultiple(String topic, boolean multipleTopic, int indice) {
        if(multipleTopic) {
            return topic + "_" + indice;
        }
        else {
            return topic;
        }
    }
}
