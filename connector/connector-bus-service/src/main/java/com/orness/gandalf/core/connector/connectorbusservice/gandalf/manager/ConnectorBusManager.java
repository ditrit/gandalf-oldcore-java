package com.orness.gandalf.core.connector.connectorbusservice.gandalf.manager;

import com.orness.gandalf.core.connector.connectorbusservice.specific.kafka.consumer.ConnectorBusConsumer;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Order(1)
public class ConnectorBusManager {

    @Autowired
    private GenericWebApplicationContext context;

    private String brokerAddress;

    //private HashMap<String, Topic> subscriptions;
    private Map<String, Object> configs;

    @Autowired
    public ConnectorBusManager(@Value("${gandalf.bus.broker}") String brokerAddress) {
        this.brokerAddress = brokerAddress;
        configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAddress);
        //this.subscriptions = new HashMap<>();
    }


    public void createTopic(String topic) {
        AdminClient adminClient = AdminClient.create(configs);
        this.createTopicBus(topic, adminClient);
        this.createTopicBusConsumer(topic, adminClient);
        adminClient.close();
    }

    public void deleteTopic(String topic) {
        AdminClient adminClient = AdminClient.create(configs);
        //List<DeleteTo> deleteTopics = new ArrayList<NewTopic>();
        //deleteTopics.add(topic);
        //adminClient.deleteTopics()
    }

    private void createTopicBus(String topic, AdminClient adminClient) {
        if(!this.isTopicReady(topic, adminClient)) {
            System.out.println("BUS CREATE TOPIC");
            NewTopic newTopic = new NewTopic(topic, 1, (short)1);

            List<NewTopic> createTopics = new ArrayList<NewTopic>();
            createTopics.add(newTopic);
            adminClient.createTopics(createTopics);
        }
    }

    private void createTopicBusConsumer(String topic, AdminClient adminClient) {
        while(!this.isTopicReady(topic, adminClient)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("SLEEP " + topic);
        }
        String beanName = topic + "ConnectorBusConsumer";
        if(!this.context.containsBean(beanName)) {
            System.out.println("BUS CREATE CONSUMER");
            this.context.registerBean(beanName, ConnectorBusConsumer.class, () -> new ConnectorBusConsumer(topic));
            ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");

            ConnectorBusConsumer connectorBusConsumer = (ConnectorBusConsumer) context.getBean(beanName);
            taskExecutor.execute(connectorBusConsumer);
        }
    }

    private boolean isTopicReady(String topic, AdminClient adminClient) {
        ListTopicsResult listTopics = adminClient.listTopics();
        try {
            return  listTopics.names().get().contains(topic);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

