package com.orness.core.databasebusservice;

import com.orness.core.databasebusservice.consumer.DatabaseBusConsumer;
import com.orness.core.databasebusservice.producer.DatabaseBusProducer;
import com.orness.core.messagebusmodule.domain.MessageBus;
import com.orness.core.messagebusmodule.repository.MessageBusRepository;
import org.hamcrest.core.IsNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DatabaseBusServiceApplicationTests {

	@Test
	public void test() {
/*
		//ENTITY
		MessageBus messageBus = new MessageBus("test_save_id", "test_save_nom", "test_save_topic", "test_save_content");

		assertThat(messageBus.getWorkflow_id(), equals("test_id"));
		assertThat(messageBus.getWorkflow_name(), equals("test_nom"));
		assertThat(messageBus.getWorkflow_topic(), equals("test_topic"));
		assertThat(messageBus.getWorkflow_content(), equals("test_content"));

		//REPOSITORY
		MessageBusRepository messageBusRepositoryMock = mock(MessageBusRepository.class);

		//SAVE
		messageBus = messageBusRepositoryMock.save(messageBus);
		assertThat(messageBus.getId(), is(notNullValue()));
		assertThat(messageBus.getWorkflow_id(), equals("test_id"));
		assertThat(messageBus.getWorkflow_name(), equals("test_nom"));
		assertThat(messageBus.getWorkflow_topic(), equals("test_topic"));
		assertThat(messageBus.getWorkflow_content(), equals("test_content"));

		//KAFKA
		DatabaseBusConsumer databaseBusConsumer = mock(DatabaseBusConsumer.class);
		DatabaseBusProducer databaseBusProducer = mock(DatabaseBusProducer.class);
		messageBus = new MessageBus("test_kafka_id_0", "test_kafka_nom_0", "test_kafka_topic_0", "test_kafka_content_0");

		//PRODUCER
		databaseBusProducer.sendDatabaseMessageKafka(messageBus);
		MessageBus testMessageBus = messageBusRepositoryMock.findByWorkflowId("test_kafka_id_0");
		assertThat(testMessageBus, is(IsNull.notNullValue()));

		//CONSUMER
		messageBus = new MessageBus("test_kafka_id_1", "test_kafka_nom_1", "test_kafka_topic_1", "test_kafka_content_1");
		databaseBusConsumer.databaseMessageKafkaListener(messageBus);
		testMessageBus = messageBusRepositoryMock.findByWorkflowId("test_kafka_id_1");
		assertThat(testMessageBus, is(IsNull.notNullValue()));
*/
	}

}
