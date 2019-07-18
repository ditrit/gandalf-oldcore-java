package com.orness.gandalf.core.service.workflowservice.manager;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.events.DeploymentEvent;
import io.zeebe.client.api.events.WorkflowInstanceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

import static com.orness.gandalf.core.module.constantmodule.workflow.WorkflowConstant.KEY_VARIABLE_WORKFLOW_ID;

@Component
public class WorkflowManager {

    ZeebeClient zeebeClient;

    @Autowired
    public WorkflowManager(ZeebeClient zeebeClient) {
        this.zeebeClient = zeebeClient;
    }

    public DeploymentEvent deployWorkflow(String workflow_name) {
        DeploymentEvent deploymentEvent = zeebeClient.newDeployCommand()
                .addResourceFromClasspath(workflow_name)
                .send().join();

        return deploymentEvent;
    }

    public WorkflowInstanceEvent InstanceWorkflow(HashMap<String, String> workflow_variables) {
        String workflow_bpmn_process_id = workflow_variables.get(KEY_VARIABLE_WORKFLOW_ID);
        System.out.println(workflow_bpmn_process_id);
        
        //INSTANCE
        return zeebeClient.newCreateInstanceCommand()
                .bpmnProcessId(workflow_bpmn_process_id)
                .latestVersion()
                .variables(workflow_variables)
                .send().join();
    }

    public String getIdDeployment(DeploymentEvent deploymentEvent) {
        return deploymentEvent.getWorkflows().get(0).getBpmnProcessId();
    }

}
