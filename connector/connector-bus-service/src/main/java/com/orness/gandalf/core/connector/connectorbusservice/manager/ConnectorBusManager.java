package com.orness.gandalf.core.connector.connectorbusservice.manager;

import com.orness.gandalf.core.connector.connectorbusservice.consumer.ConnectorBusConsumer;
import com.orness.gandalf.core.module.messagebusmodule.domain.MessageBus;
import com.orness.gandalf.core.module.subscribertopicmodule.domain.Subscriber;
import com.orness.gandalf.core.module.subscribertopicmodule.domain.Topic;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ConnectorBusManager {

    private GenericWebApplicationContext applicationContext;
    private HashMap<String, Topic> subscriptions;
    private String bootstrapAddress = "localhost:9092";
    private Map<String, Object> configs;

    @Autowired
    public ConnectorBusManager(GenericWebApplicationContext  applicationContext) {
        configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        this.subscriptions = new HashMap<>();
        //USELESS
        this.applicationContext = applicationContext;
    }

    public Topic topicCreation(String topic_name) {
        Topic topic = new Topic(topic_name);
        this.addTopicBusAndTopicBusListener(topic);
        this.subscriptions.put(topic.getName(), topic);
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
        topic.getSubscribers().add(this.createNewSubscriber(subscriber_name));
    }

    public void topicUnsubscription(String topic_name, String subscriber_name) {
        Topic topic = this.subscriptions.get(topic_name);
        this.removeSubscriberInTopic(topic, subscriber_name);
    }

    public boolean isSubscriberIndexValid(String topic_name, String subscriber_name) {
        Topic topic = this.subscriptions.get(topic_name);
        Subscriber subscriber = this.getSubscriberByNameInTopic(topic, subscriber_name);
        if(subscriber != null) {
            if(subscriber.getIndex() < topic.getMessageBusLinkedList().size()) {
                return true;
            }
        }
        return false;
    }

    public boolean isSubscriberInTopic(String topic_name, String subscriber_name) {
        Subscriber subscriber = this.getSubscriberByNameInTopic(this.subscriptions.get(topic_name), subscriber_name);
        if(subscriber != null) {
            return true;
        }
        return false;
    }

    public MessageBus getMessageTopicBySubscriber(String topic_name, String subscriber_name) {
        //Topic topic = this.subscriptions.get(topic_name);
        return this.getMessageBusBySubscriberIndexInTopic(this.subscriptions.get(topic_name), subscriber_name);
    }

    //
    private Subscriber createNewSubscriber(String name) {
        return new Subscriber(name);
    }

    private void removeSubscriberInTopic(Topic topic, String name) {
        topic.getSubscribers().remove(this.getSubscriberByNameInTopic(topic, name));
    }

    private MessageBus getMessageBusBySubscriberIndexInTopic(Topic topic, String subscriber_name)  {
        Subscriber subscriber = this.getSubscriberByNameInTopic(topic, subscriber_name);
        MessageBus messageBus = null;
        if(this.isSubscriberIndexValid(topic.getName(), subscriber.getName())) {
            messageBus = topic.getMessageBusLinkedList().get(subscriber.getIndex());
            subscriber.IncrementIndex();
        }
        System.out.println("GetMessage return " + messageBus);
        return messageBus;
    }

    private Subscriber getSubscriberByNameInTopic(Topic topic, String subscriber_name) {
        for(Subscriber topic_workflow : topic.getSubscribers()) {
           if(topic_workflow.getName().equals(subscriber_name)) {
               return topic_workflow;
           }
        }
        return null;
    }

    private void addTopicBusAndTopicBusListener(Topic topic) {
        this.addTopicBus(topic.getName());
        this.addTopicBusContainer(topic);
        //this.addTopicBusListener(topic);
    }

    private void addTopicBusContainer(Topic topic) {
        ConnectorBusConsumer container = new ConnectorBusConsumer(topic);
    }

    private void addTopicBus(String topic) {
        AdminClient adminClient = AdminClient.create(configs);
        NewTopic newTopic = new NewTopic(topic, 1, (short)1);
        //new NewTopic(topicName, numPartitions, replicationFactor)
        List<NewTopic> newTopics = new ArrayList<NewTopic>();
        newTopics.add(newTopic);
        adminClient.createTopics(newTopics);
        adminClient.close();
/*        if(!applicationContext.containsBeanDefinition(topic)) {
            applicationContext.registerBean(topic, NewTopic.class, () -> new NewTopic(topic,1, (short) 1));
        }*/
    }

    private void removeTopicBusAndTopicBusListener(String topic) {
        this.removeTopicBus(topic);
        this.removeTopicBusListener(topic);
    }

    private void removeTopicBus(String topic) {
        applicationContext.removeBeanDefinition(topic);
    }

    //TODO REVOIR
    private void removeTopicBusListener(String topic) {
        applicationContext.removeBeanDefinition(topic+"BusListenerBean");
    }

}

