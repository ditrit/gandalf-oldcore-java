package com.orness.core.connectorbusservice.domain;

import com.orness.core.connectorbusservice.consumer.ConnectorBusListener;
import com.orness.core.messagebusmodule.domain.MessageBus;
import com.orness.core.workflowtopicmodule.domain.Topic;
import com.orness.core.workflowtopicmodule.domain.Workflow;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.GenericWebApplicationContext;

import java.util.HashMap;

@Component
public class ConnectorBusManager {

    private GenericWebApplicationContext applicationContext;
    private HashMap<String, Topic> subscriptions;

    @Autowired
    public ConnectorBusManager(GenericWebApplicationContext  applicationContext) {
        this.applicationContext = applicationContext;
        this.subscriptions = new HashMap<>();
    }

    public void topicCreation(String name) {
        Topic topic = new Topic(name);
        this.addTopicBusAndTopicBusListener(topic);
        this.subscriptions.put(topic.getName(), topic);
    }

    public void topicSuppression(String topic) {
        this.removeTopicBusAndTopicBusListener(topic);
        this.subscriptions.remove(topic);
    }

    public void topicSubscription(String topic_name, String workflow_name) {
        Topic topic = this.subscriptions.get(topic_name);
        topic.getWorkflows().add(this.createNewWorkflow(workflow_name));
    }

    public void topicUnsubscription(String topic_name, String workflow_name) {
        Topic topic = this.subscriptions.get(topic_name);
        this.removeWorkflowInTopic(topic, workflow_name);
    }

    public MessageBus getMessageTopicByWorkflow(String topic_name, String workflow_name) {
        Topic topic = this.subscriptions.get(topic_name);
        return this.getMessageBusByWorkflowIndexInTopic(topic, workflow_name);
    }

    private Workflow createNewWorkflow(String name) {
        return new Workflow(name);
    }

    private void removeWorkflowInTopic(Topic topic, String name) {
        topic.getWorkflows().remove(this.getWorkflowByNameInTopic(topic, name));
    }

    private MessageBus getMessageBusByWorkflowIndexInTopic(Topic topic, String workflow_name)  {
        return topic.getMessageBusLinkedList().get(this.getWorkflowByNameInTopic(topic, workflow_name).getIndex());
    }

    private Workflow getWorkflowByNameInTopic(Topic topic, String name) {
        for(Workflow topic_workflow : topic.getWorkflows()) {
           if(topic_workflow.getName().equals(name)) {
               return topic_workflow;
           }
        }
        return null;
    }

    private void addTopicBusAndTopicBusListener(Topic topic) {
        this.addTopicBus(topic.getName());
        this.addTopicBusListener(topic);
    }

    private void addTopicBusListener(Topic topic) {
        applicationContext.registerBean(topic.getName()+"BusListenerBean", ConnectorBusListener.class, () -> new ConnectorBusListener(topic));
    }

    private void addTopicBus(String topic) {
        applicationContext.registerBean(topic, NewTopic.class, () -> new NewTopic(topic,1, (short) 1));
    }

    private void removeTopicBusAndTopicBusListener(String topic) {
        this.removeTopicBus(topic);
        this.removeTopicBusListener(topic);
    }

    private void removeTopicBus(String topic) {
        applicationContext.removeBeanDefinition(topic+"BusListenerBean");
    }

    private void removeTopicBusListener(String topic) {
        applicationContext.removeBeanDefinition(topic);
    }

}

