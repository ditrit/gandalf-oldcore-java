package com.orness.gandalf.core.module.kafkamodule.common.manager;

import com.orness.gandalf.core.module.busmodule.manager.BusCommonManager;
import com.orness.gandalf.core.module.kafkamodule.core.producer.KafkaProducer;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class KafkaCommonManager extends BusCommonManager {

    private KafkaAdmin kafkaAdmin;
    private KafkaProducer kafkaProducer;

    @Autowired
    public KafkaCommonManager(KafkaAdmin kafkaAdmin, KafkaProducer kafkaProducer) {
        this.kafkaAdmin = kafkaAdmin;
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void createTopic(String topic) {
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
    public void deleteTopic(String topic) {
        AdminClient adminClient = AdminClient.create(this.kafkaAdmin.getConfig());
        if(!this.isTopicExist(topic, adminClient)) {
            List<String> deleteTopics = new ArrayList<>();
            deleteTopics.add(topic);
            adminClient.deleteTopics(deleteTopics);
        }
        adminClient.close();
    }

    @Override
    public void sendMessage(String topic, String message) {
        this.kafkaProducer.sendKafka(topic, message);
    }

    @Override
    public void receiveMessage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void synchronizeGandalf() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void synchronizeBus() {
        throw new UnsupportedOperationException("Not supported yet.");
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
