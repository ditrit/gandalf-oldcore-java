package com.orness.gandalf.core.module.kafkamodule.normative.manager;

import com.orness.gandalf.core.module.busmodule.properties.ConnectorBusProperties;
import com.orness.gandalf.core.module.busmodule.manager.ConnectorBusNormativeManager;
import com.orness.gandalf.core.module.gandalfmodule.worker.event.GandalfPublisherEvent;
import com.orness.gandalf.core.module.gandalfmodule.worker.event.GandalfSubscriberEventService;
import com.orness.gandalf.core.module.gandalfmodule.properties.GandalfProperties;
import com.orness.gandalf.core.module.kafkamodule.core.consumer.gandalf.KafkaGandalfEventConsumer;
import com.orness.gandalf.core.module.kafkamodule.core.producer.KafkaProducer;
import com.orness.gandalf.core.module.kafkamodule.core.properties.KafkaProperties;
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

@Component(value = "commonManager")
@Profile(value = "kafka-module")
public class ConnectorKafkaNormativeManager extends ConnectorBusNormativeManager {

    private KafkaAdmin kafkaAdmin;
    private KafkaProducer kafkaProducer;
    private ApplicationContext context;
    private GandalfPublisherEvent gandalfPublisherEvent;
    private GandalfProperties gandalfProperties;
    private ConnectorBusProperties connectorBusProperties;
    private KafkaProperties kafkaProperties;
    private GandalfSubscriberEventService gandalfSubscriberEventService;

    @Autowired
    public ConnectorKafkaNormativeManager(KafkaAdmin kafkaAdmin, KafkaProducer kafkaProducer, ApplicationContext context, GandalfPublisherEvent gandalfPublisherEvent, GandalfProperties gandalfProperties, ConnectorBusProperties connectorBusProperties, KafkaProperties kafkaProperties, GandalfSubscriberEventService gandalfSubscriberEventService) {
        this.context = context;
        this.kafkaAdmin = kafkaAdmin;
        this.kafkaProducer = kafkaProducer;
        this.gandalfPublisherEvent = gandalfPublisherEvent;
        this.gandalfProperties = gandalfProperties;
        this.kafkaProperties = kafkaProperties;
        this.connectorBusProperties = connectorBusProperties;
        this.gandalfSubscriberEventService = gandalfSubscriberEventService;
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
    public String receiveMessage(String topic) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void synchronizeToGandalf(String topic) {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        KafkaGandalfEventConsumer kafkaGandalfEventConsumer = new KafkaGandalfEventConsumer(topic, this.gandalfPublisherEvent, this.kafkaProperties, this.connectorBusProperties);
        taskExecutor.execute(kafkaGandalfEventConsumer);
    }

    @Override
    public void synchronizeToBus(String topic) {
        this.gandalfSubscriberEventService.addInstanceByTopic(topic);
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
