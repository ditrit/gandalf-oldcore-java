package com.orness.gandalf.core.workflowservice;

import com.orness.gandalf.core.workflowservice.manager.WorkflowManager;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.events.DeploymentEvent;
import io.zeebe.client.api.events.WorkflowInstanceEvent;
import io.zeebe.test.ZeebeTestRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WorkflowServiceApplicationTests {

	@Rule
	public final ZeebeTestRule testRule = new ZeebeTestRule();

	private ZeebeClient client;
	private WorkflowManager workflowManager;
	private DeploymentEvent deploymentEvent;
	private  WorkflowInstanceEvent workflowInstance;
	@Test
	public void test() {
		String workflow_name = "diagram_test.bpmn";
		client = testRule.getClient();
		workflowManager = new WorkflowManager(client);

		//DEPLOY
		deploymentEvent = workflowManager.deployWorkflow(workflow_name);
		String workflow_process_id = workflowManager.getIdDeployment(deploymentEvent);

		//INSTANCE
		workflowInstance = workflowManager.InstanceWorkflow(workflow_process_id, workflow_name, workflow_name, "test", "test");

		// TEST
		ZeebeTestRule.assertThat(workflowInstance).hasPassed("start");
		ZeebeTestRule.assertThat(workflowInstance).hasVariables("workflow_process_id" ,workflow_process_id);
		ZeebeTestRule.assertThat(workflowInstance).hasVariables("workflow_id" ,workflow_name);
		ZeebeTestRule.assertThat(workflowInstance).hasVariables("workflow_name" ,workflow_name);
		ZeebeTestRule.assertThat(workflowInstance).hasVariables("workflow_topic" ,"test");
		ZeebeTestRule.assertThat(workflowInstance).hasVariables("workflow_content" ,"test");
	}
}
