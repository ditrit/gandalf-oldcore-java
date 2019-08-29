package com.orness.gandalf.core.module.kafkamodule.normative.manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.orness.gandalf.core.library.gandalfjavaclient.GandalfJavaClient;
import com.orness.gandalf.core.library.gandalfjavaclient.core.GandalfPublisherZeroMQ;
import com.orness.gandalf.core.module.busmodule.properties.ConnectorBusProperties;
import com.orness.gandalf.core.module.busmodule.manager.ConnectorBusNormativeManager;
import com.orness.gandalf.core.module.kafkamodule.core.consumer.gandalf.KafkaGandalfEventConsumer;
import com.orness.gandalf.core.module.kafkamodule.core.producer.KafkaProducer;
import com.orness.gandalf.core.module.kafkamodule.properties.ConnectorKafkaProperties;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(value = "normativeManager")
@Profile(value = "kafka-module")
public class ConnectorKafkaNormativeManager extends ConnectorBusNormativeManager {

    private KafkaAdmin kafkaAdmin;
    private KafkaProducer kafkaProducer;
    private ApplicationContext context;
    private GandalfJavaClient gandalfJavaClient;
    private GandalfPublisherZeroMQ gandalfPublisherZeroMQ;
    private ConnectorBusProperties connectorBusProperties;
    private ConnectorKafkaProperties connectorKafkaProperties;
    private Gson mapper;

    @Autowired
    public ConnectorKafkaNormativeManager(GandalfPublisherZeroMQ gandalfPublisherZeroMQ, KafkaAdmin kafkaAdmin, KafkaProducer kafkaProducer, ConnectorBusProperties connectorBusProperties, ConnectorKafkaProperties connectorKafkaProperties) {
        this.context = context;
        this.kafkaAdmin = kafkaAdmin;
        this.kafkaProducer = kafkaProducer;
        this.gandalfPublisherZeroMQ = gandalfPublisherZeroMQ;
        this.connectorKafkaProperties = connectorKafkaProperties;
        this.connectorBusProperties = connectorBusProperties;
        this.mapper = new Gson();
    }

    @Override
    public void createTopic(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        String topic = jsonObject.get("topic").getAsString();

        AdminClient adminClient = AdminClient.create(this.kafkaAdmin.getConfig());
        if(!this.isTopicExist(topic, adminClient)) {
            NewTopic newTopic = new NewTopic(topic, 1, (short)1);
            List<NewTopic> createTopics = new ArrayList<>();
            createTopics.add(newTopic);
            adminClient.createTopics(createTopics);
        }
        adminClient.close();
    }

    @Override
    public void deleteTopic(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        String topic = jsonObject.get("topic").getAsString();

        AdminClient adminClient = AdminClient.create(this.kafkaAdmin.getConfig());
        if(!this.isTopicExist(topic, adminClient)) {
            List<String> deleteTopics = new ArrayList<>();
            deleteTopics.add(topic);
            adminClient.deleteTopics(deleteTopics);
        }
        adminClient.close();
    }

    @Override
    public void sendMessage(String payload) {
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        this.kafkaProducer.sendKafka(jsonObject.get("topic").getAsString(), jsonObject.get("message").getAsString());
    }

    @Override
    public String receiveMessage(String payload) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void synchronizeToGandalf(String payload) {
        //TODO REVOIR
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        KafkaGandalfEventConsumer kafkaGandalfEventConsumer = new KafkaGandalfEventConsumer(this.gandalfPublisherZeroMQ, this.connectorKafkaProperties, this.connectorBusProperties);
        taskExecutor.execute(kafkaGandalfEventConsumer);
    }

    @Override
    public void synchronizeToBus(String payload) {
        //TODO ADD TOPICS
        //this.gandalfSubscriberEventService.addInstanceByTopic(topic);
        //this.gandalfSubscriberEventService
    }

    private boolean isTopicExist(String topic, AdminClient adminClient) {
        ListTopicsResult listTopics = adminClient.listTopics();
        try {
            return  listTopics.names().get().contains(topic);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
