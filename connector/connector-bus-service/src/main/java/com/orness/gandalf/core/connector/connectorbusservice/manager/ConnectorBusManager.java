package com.orness.gandalf.core.connector.connectorbusservice.manager;

import com.orness.gandalf.core.connector.connectorbusservice.consumer.ConnectorBusConsumer;
import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import com.orness.gandalf.core.module.subscribertopicmodule.domain.Subscriber;
import com.orness.gandalf.core.module.subscribertopicmodule.domain.Topic;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
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
        System.out.println("TOPIC SUB " + topic);
        if(topic == null) {
            topic = this.topicCreation(topic_name);
        }
        topic.getSubscribers().add(this.createNewSubscriber(subscriber_name, topic));
    }

    public void topicUnsubscription(String topic_name, String subscriber_name) {
        Topic topic = this.subscriptions.get(topic_name);
        this.removeSubscriberInTopic(topic, subscriber_name);
    }

  /*  public boolean isSubscriberIndexValid(String topic_name, String subscriber_name) {
        Topic topic = this.subscriptions.get(topic_name);
        System.out.println("TOPIC " + topic);
        Subscriber subscriber = this.getSubscriberByNameInTopic(topic, subscriber_name);
        System.out.println("SUBS " + subscriber);
        if(subscriber != null) {
            System.out.println("MESSAGES " + topic.getMessageLinkedList().size());
            if(subscriber.getIndex() < topic.getMessageLinkedList().size()) {
                System.out.println("TRUE");
                return true;
            }
        }
        System.out.println("FALSE");
        return false;
    }*/

    public boolean isSubscriberInTopic(String topic_name, String subscriber_name) {
        Subscriber subscriber = this.getSubscriberByNameInTopic(this.subscriptions.get(topic_name), subscriber_name);
        if(subscriber != null) {
            return true;
        }
        return false;
    }

/*    public MessageGandalf getMessageTopicBySubscriber(String topic_name, String subscriber_name) {
        //Topic topic = this.subscriptions.get(topic_name);
        return this.getMessageBusBySubscriberIndexInTopic(this.subscriptions.get(topic_name), subscriber_name);
    }*/

    //
    private Subscriber createNewSubscriber(String name, Topic topic) {
        return new Subscriber(name, topic);
    }

    private void removeSubscriberInTopic(Topic topic, String name) {
        Subscriber subscriber = this.getSubscriberByNameInTopic(topic, name);
        subscriber.stopSubscriberZeroMQ();
        topic.getSubscribers().remove(subscriber);
    }

   /* private MessageGandalf getMessageBusBySubscriberIndexInTopic(Topic topic, String subscriber_name)  {
        Subscriber subscriber = this.getSubscriberByNameInTopic(topic, subscriber_name);
        MessageGandalf messageGandalf = null;
        System.out.println("GetSub" + subscriber);
        if(this.isSubscriberIndexValid(topic.getName(), subscriber.getName())) {
            System.out.println("valid");
            messageGandalf = topic.getMessageLinkedList().get(subscriber.getIndex());
            subscriber.IncrementIndex();
        }
        System.out.println("GetMessage return " + messageGandalf);
        return messageGandalf;
    }*/

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
        //this.addTopicBusListener(topic);
    }

    private void addTopicBusContainer(Topic topic) {
        System.out.println("ADD CONTAINER");
        AdminClient adminClient = AdminClient.create(configs);
        System.out.println("ISREADY " + this.isTopicReady(topic.getName(), adminClient));
        while(!this.isTopicReady(topic.getName(), adminClient)) {
            System.out.println("ISREADY 2 " + this.isTopicReady(topic.getName(), adminClient));
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("ADD CONTAINER");
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
        System.out.println("ADD TOPIC");
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

