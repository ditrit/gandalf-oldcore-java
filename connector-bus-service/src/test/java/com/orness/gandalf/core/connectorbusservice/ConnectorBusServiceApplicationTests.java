package com.orness.gandalf.core.connectorbusservice;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectorBusServiceApplicationTests {

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
		/*//TODO VOIR TEST JUNIT GRPC
		//GRPC
		ConnectorBusGrpc connectorKafkaService = mock(ConnectorBusGrpc.class);

		//INPUT
		//ConnectorBusGrpc.message()
		//OUTPUT

		//KAFKA
		ConnectorBusManager connectorKafkaConsumer = mock(ConnectorBusManager.class);
		ConnectorBusProducer connectorBusProducer = mock(ConnectorBusProducer.class);
		MessageBus messageBus = new MessageBus("test_kafka_id_0", "test_kafka_nom_0", "test_kafka_topic_0", "test_kafka_content_0");

		//TODO VOIR TOPIC DYNAMIQUE
		//TOPIC

		//TODO CREATE TOPIC TEST
		//PRODUCER
		connectorBusProducer.sendConnectorMessageKafka("test", messageBus);
		assertThat(outContent.toString(), containsString("test_kafka_id_0"));
		assertThat(outContent.toString(), containsString("test_kafka_nom_0"));
		assertThat(outContent.toString(), containsString("test_kafka_topic_0"));
		assertThat(outContent.toString(), containsString("test_kafka_content_0"));

		//CONSUMER*/
	}

}
