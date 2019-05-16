package com.orness.core.databasekafkaservice;

import com.orness.core.databasekafkaservice.consumer.DatabaseKafkaConsumer;
import com.orness.core.databasekafkaservice.producer.DatabaseKafkaProducer;
import com.orness.core.messagekafkamodule.domain.MessageKafka;
import com.orness.core.messagekafkamodule.repository.MessageKafkaRepository;
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
public class DatabaseKafkaServiceApplicationTests {

	@Test
	public void test() {

		//ENTITY
		MessageKafka messageKafka = new MessageKafka("test_save_id", "test_save_nom", "test_save_topic", "test_save_content");

		assertThat(messageKafka.getWorkflow_id(), equals("test_id"));
		assertThat(messageKafka.getWorkflow_name(), equals("test_nom"));
		assertThat(messageKafka.getWorkflow_topic(), equals("test_topic"));
		assertThat(messageKafka.getWorkflow_content(), equals("test_content"));

		//REPOSITORY
		MessageKafkaRepository messageKafkaRepositoryMock = mock(MessageKafkaRepository.class);

		//SAVE
		messageKafka = messageKafkaRepositoryMock.save(messageKafka);
		assertThat(messageKafka.getId(), is(notNullValue()));
		assertThat(messageKafka.getWorkflow_id(), equals("test_id"));
		assertThat(messageKafka.getWorkflow_name(), equals("test_nom"));
		assertThat(messageKafka.getWorkflow_topic(), equals("test_topic"));
		assertThat(messageKafka.getWorkflow_content(), equals("test_content"));

		//KAFKA
		DatabaseKafkaConsumer databaseKafkaConsumer = mock(DatabaseKafkaConsumer.class);
		DatabaseKafkaProducer databaseKafkaProducer = mock(DatabaseKafkaProducer.class);
		messageKafka = new MessageKafka("test_kafka_id_0", "test_kafka_nom_0", "test_kafka_topic_0", "test_kafka_content_0");

		//PRODUCER
		databaseKafkaProducer.sendDatabaseMessageKafka(messageKafka);
		MessageKafka testMessageKafka = messageKafkaRepositoryMock.findByWorkflowId("test_kafka_id_0");
		assertThat(testMessageKafka, is(IsNull.notNullValue()));

		//CONSUMER
		messageKafka = new MessageKafka("test_kafka_id_1", "test_kafka_nom_1", "test_kafka_topic_1", "test_kafka_content_1");
		databaseKafkaConsumer.databaseMessageKafkaListener(messageKafka);
		testMessageKafka = messageKafkaRepositoryMock.findByWorkflowId("test_kafka_id_1");
		assertThat(testMessageKafka, is(IsNull.notNullValue()));

	}

}
