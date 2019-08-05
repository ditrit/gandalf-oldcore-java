package com.orness.gandalf.core.module.kafkamodule.common.manager;

import com.orness.gandalf.core.module.busmodule.manager.BusCommonManager;
import com.orness.gandalf.core.module.kafkamodule.core.consumer.gandalf.KafkaGandalfEventConsumer;
import com.orness.gandalf.core.module.kafkamodule.core.producer.GandalfKafkaSubscriber;
import com.orness.gandalf.core.module.kafkamodule.core.producer.KafkaProducer;
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

//TODO REVOIR PROPS
@Component(value = "commonManager")
@Profile(value = "kafka-module")
public class KafkaCommonManager extends BusCommonManager {

    private KafkaAdmin kafkaAdmin;
    private KafkaProducer kafkaProducer;
    private ApplicationContext context;

    @Autowired
    public KafkaCommonManager(KafkaAdmin kafkaAdmin, KafkaProducer kafkaProducer, ApplicationContext context) {
        this.context = context;
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
    public void synchronizeToGandalf(String topic) {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        KafkaGandalfEventConsumer kafkaGandalfEventConsumer = new KafkaGandalfEventConsumer(topic);
        taskExecutor.execute(kafkaGandalfEventConsumer);
    }

    @Override
    public void synchronizeToBus(String topic) {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        GandalfKafkaSubscriber gandalfKafkaSubscriber = new GandalfKafkaSubscriber("", "");
        taskExecutor.execute(gandalfKafkaSubscriber);
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
