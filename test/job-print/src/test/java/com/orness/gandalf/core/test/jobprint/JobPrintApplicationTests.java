package com.orness.gandalf.core.test.jobprint;

import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.events.DeploymentEvent;
import io.zeebe.client.api.events.WorkflowInstanceEvent;
import io.zeebe.test.ZeebeTestRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JobPrintApplicationTests {

	@Rule
	public final ZeebeTestRule testRule = new ZeebeTestRule();

	private ZeebeClient client;
	private DeploymentEvent deploymentEvent;
	private WorkflowInstanceEvent workflowInstance;
	private PrintStream sysOut;
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


	@Before
	public void setUpStreams() {
		sysOut = System.out;
		System.setOut(new PrintStream(outContent));
	}

	@After
	public void revertStreams() {
		System.setOut(sysOut);
	}

	@Test
	public void test() {
		/*String workflow_name = "diagram_test_print.bpmn";
		String job_key = "";

		client = testRule.getClient();

		//DEPLOY
		deploymentEvent = client.newDeployCommand()
				.addResourceFromClasspath(workflow_name)
				.send().join();

		String workflow_process_id = deploymentEvent.getWorkflows().get(0).getBpmnProcessId();

		//INSTANCE
		HashMap<String, String> workflow_variables = new HashMap();
		workflow_variables.put("workflow_process_id", workflow_process_id);
		workflow_variables.put("workflow_id", workflow_name);
		workflow_variables.put("workflow_name", workflow_name);
		workflow_variables.put("workflow_topic", "test");
		workflow_variables.put("workflow_content", "test");

		workflowInstance = client.newCreateInstanceCommand()
				.bpmnProcessId(workflow_process_id)
				.latestVersion()
				.variables(workflow_variables)
				.send().join();

		// TEST
		//INSTANCE
		ZeebeTestRule.assertThat(workflowInstance).hasPassed("start");
		ZeebeTestRule.assertThat(workflowInstance).hasVariables("workflow_process_id" ,workflow_process_id);
		ZeebeTestRule.assertThat(workflowInstance).hasVariables("workflow_id" ,workflow_name);
		ZeebeTestRule.assertThat(workflowInstance).hasVariables("workflow_name" ,workflow_name);
		ZeebeTestRule.assertThat(workflowInstance).hasVariables("workflow_topic" ,"test");
		ZeebeTestRule.assertThat(workflowInstance).hasVariables("workflow_content" ,"test");

		//JOB
		assertThat(outContent.toString(), containsString("PRINT"));
		assertThat(outContent.toString(), containsString("PRINT PROCESS ID " + workflow_process_id));
		assertThat(outContent.toString(), containsString("PRINT ID " + workflow_name));
		assertThat(outContent.toString(), containsString("PRINT NAME " + workflow_name));
		assertThat(outContent.toString(), containsString("PRINT TOPIC test"));
		assertThat(outContent.toString(), containsString("PRINT CONTENT test"));

		ZeebeTestRule.assertThat(workflowInstance).hasCompleted(job_key);
		ZeebeTestRule.assertThat(workflowInstance).isEnded();*/
	}

}
