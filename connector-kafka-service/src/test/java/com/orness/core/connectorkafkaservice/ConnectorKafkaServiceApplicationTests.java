package com.orness.core.connectorkafkaservice;

import com.orness.core.connectorkafkaservice.grpc.ConnectorKafkaGrpc;
import com.orness.core.connectorkafkaservice.domain.ConnectorKafkaManager;
import com.orness.core.connectorkafkaservice.producer.ConnectorKafkaProducer;
import com.orness.core.messagekafkamodule.domain.MessageKafka;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectorKafkaServiceApplicationTests {

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
		//TODO VOIR TEST JUNIT GRPC
		//GRPC
		ConnectorKafkaGrpc connectorKafkaService = mock(ConnectorKafkaGrpc.class);

		//INPUT
		//ConnectorKafkaGrpc.message()
		//OUTPUT

		//KAFKA
		ConnectorKafkaManager connectorKafkaConsumer = mock(ConnectorKafkaManager.class);
		ConnectorKafkaProducer connectorKafkaProducer = mock(ConnectorKafkaProducer.class);
		MessageKafka messageKafka = new MessageKafka("test_kafka_id_0", "test_kafka_nom_0", "test_kafka_topic_0", "test_kafka_content_0");

		//TODO VOIR TOPIC DYNAMIQUE
		//TOPIC

		//TODO CREATE TOPIC TEST
		//PRODUCER
		connectorKafkaProducer.sendConnectorMessageKafka("test", messageKafka);
		assertThat(outContent.toString(), containsString("test_kafka_id_0"));
		assertThat(outContent.toString(), containsString("test_kafka_nom_0"));
		assertThat(outContent.toString(), containsString("test_kafka_topic_0"));
		assertThat(outContent.toString(), containsString("test_kafka_content_0"));

		//CONSUMER
	}

}
