package com.orness.gandalf.core.connector.connectorbusservice.manager;

import com.orness.gandalf.core.connector.connectorbusservice.consumer.ConnectorBusConsumer;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ConnectorBusManager {

    @Value("${gandalf.bus.broker}")
    private String brokerAddress;

    //private HashMap<String, Topic> subscriptions;
    private Map<String, Object> configs;

    @Autowired
    public ConnectorBusManager() {
        configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, brokerAddress);
        //this.subscriptions = new HashMap<>();
    }


    public void createTopic(String topic) {
        AdminClient adminClient = AdminClient.create(configs);
        if(!isTopicReady(topic, adminClient)) {
            this.createTopicBus(topic, adminClient);
            this.createTopicBusConsumer(topic, adminClient);
        }
    }

    public void deleteTopic(String topic) {
        AdminClient adminClient = AdminClient.create(configs);
        //List<DeleteTo> deleteTopics = new ArrayList<NewTopic>();
        //deleteTopics.add(topic);
        //adminClient.deleteTopics()
    }

    private void createTopicBus(String topic, AdminClient adminClient) {
        NewTopic newTopic = new NewTopic(topic, 1, (short)1);

        List<NewTopic> createTopics = new ArrayList<NewTopic>();
        createTopics.add(newTopic);
        adminClient.createTopics(createTopics);
        adminClient.close();
    }

    private void createTopicBusConsumer(String topic, AdminClient adminClient) {
        while(!this.isTopicReady(topic, adminClient)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ConnectorBusConsumer container = new ConnectorBusConsumer(topic);
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

    /*public Topic topicCreation(String topic_name) {
        Topic topic;
        if(this.isTopicExist(topic_name)) {
           topic = this.subscriptions.get(topic_name);
        }
        else {
            topic = new Topic(topic_name);
            this.addTopicBusAndTopicBusListener(topic);
            this.subscriptions.put(topic.getName(), topic);
        }

        return topic;
    }

    public void topicSuppression(String topic_name) {
        this.removeTopicBusAndTopicBusListener(topic_name);
        this.subscriptions.remove(topic_name);
    }

    public void topicSubscription(String topic_name, String subscriber_name) {
        Topic topic = this.subscriptions.get(topic_name);
        if(topic == null) {
            topic = this.topicCreation(topic_name);
        }
        topic.getSubscribers().add(this.createNewSubscriber(subscriber_name, topic));
    }

    public void topicUnsubscription(String topic_name, String subscriber_name) {
        Topic topic = this.subscriptions.get(topic_name);
        this.removeSubscriberInTopic(topic, subscriber_name);
    }

    public boolean isSubscriberInTopic(String topic_name, String subscriber_name) {
        Subscriber subscriber = this.getSubscriberByNameInTopic(this.subscriptions.get(topic_name), subscriber_name);
        if(subscriber != null) {
            return true;
        }
        return false;
    }

    private Subscriber createNewSubscriber(String name, Topic topic) {
        return new Subscriber(name, topic);
    }

    private void removeSubscriberInTopic(Topic topic, String name) {
        Subscriber subscriber = this.getSubscriberByNameInTopic(topic, name);
        subscriber.stopSubscriberZeroMQ();
        topic.getSubscribers().remove(subscriber);
    }

    public Subscriber getSubscriberByNameInTopic(Topic topic, String subscriber_name) {
        for(Subscriber topic_subscriber : topic.getSubscribers()) {
           if(topic_subscriber.getName().equals(subscriber_name)) {
               return topic_subscriber;
           }
        }
        return null;
    }

    public Subscriber getSubscriberByNameInTopic(String topic_name, String subscriber_name) {
        Topic topic = this.subscriptions.get(topic_name);
        for(Subscriber topic_subscriber : topic.getSubscribers()) {
            if(topic_subscriber.getName().equals(subscriber_name)) {
                return topic_subscriber;
            }
        }
        return null;
    }

    private void addTopicBusAndTopicBusListener(Topic topic) {
        this.addTopicBus(topic.getName());
        this.addTopicBusContainer(topic);
    }

    private void addTopicBusContainer(Topic topic) {
        AdminClient adminClient = AdminClient.create(configs);
        while(!this.isTopicReady(topic.getName(), adminClient)) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ConnectorBusConsumer container = new ConnectorBusConsumer(topic);
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

    private boolean isTopicExist(String topic) {
       return this.subscriptions.containsKey(topic);
    }

    private void addTopicBus(String topic) {
        AdminClient adminClient = AdminClient.create(configs);
        NewTopic newTopic = new NewTopic(topic, 1, (short)1);

        List<NewTopic> newTopics = new ArrayList<NewTopic>();
        newTopics.add(newTopic);
        adminClient.createTopics(newTopics);
        adminClient.close();
    }

    private void removeTopicBusAndTopicBusListener(String topic) {
        this.removeTopicBus(topic);
        this.removeTopicBusListener(topic);
    }

    //TODO REVOIR USELESS ?
    private void removeTopicBus(String topic) {

        //applicationContext.removeBeanDefinition(topic);
    }

    //TODO REVOIR USELESS ?
    private void removeTopicBusListener(String topic_name) {
        //Topic topic = this.subscriptions.get(topic_name);
        //topic.getSubscribers().remove()

    }*/

}

