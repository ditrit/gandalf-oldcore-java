package com.orness.gandalf.core.module.kafkamodule.normative.manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.orness.gandalf.core.module.busmodule.manager.ConnectorBusNormativeManager;
import com.orness.gandalf.core.module.kafkamodule.core.consumer.ConnectorKafkaConsumer;
import com.orness.gandalf.core.module.kafkamodule.core.producer.ConnectorKafkaProducer;
import com.orness.gandalf.core.module.kafkamodule.properties.ConnectorKafkaProperties;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(value = "normativeManager")
@ConditionalOnBean(ConnectorKafkaProperties.class)
public class ConnectorKafkaNormativeManager extends ConnectorBusNormativeManager {

    private KafkaAdmin kafkaAdmin;
    private ConnectorKafkaProducer connectorKafkaProducer;
    private ConnectorKafkaConsumer connectorKafkaConsumer;
    private Gson mapper;

    @Autowired
    public ConnectorKafkaNormativeManager(KafkaAdmin kafkaAdmin, ConnectorKafkaConsumer connectorKafkaConsumer, ConnectorKafkaProducer connectorKafkaProducer) {
        this.kafkaAdmin = kafkaAdmin;
        this.connectorKafkaProducer = connectorKafkaProducer;
        this.connectorKafkaConsumer = connectorKafkaConsumer;
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
        this.connectorKafkaProducer.sendKafka(jsonObject.get("topic").getAsString(), jsonObject.get("message").getAsString());
    }

    @Override
    public String receiveMessage(String payload) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void synchronizeToGandalf(String topic, String event, String payload) {

    }

    @Override
    public void synchronizeToBus(String topic, String event, String payload) {
        if(this.getSynchronizeTopics().contains(topic)) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("event", event);
            jsonObject.addProperty("payload", payload);
            System.out.println("SEND TO KAFKA");
            System.out.println(topic);
            System.out.println(jsonObject.toString());
            this.connectorKafkaProducer.sendKafka(topic, jsonObject.toString());
        }
    }

    @Override
    public void addSynchronizeTopicToGandalf(String payload) {
        String topic = payload;
        this.connectorKafkaConsumer.addTopic(payload);
    }

    @Override
    public void addSynchronizeTopicToBus(String payload) {
        System.out.println("SYNCHRONIZE TO BUS");
        System.out.println(payload);
        String topic = payload;
        if(!this.getSynchronizeTopics().contains(topic)) {
            this.getSynchronizeTopics().add(topic);
        }
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
