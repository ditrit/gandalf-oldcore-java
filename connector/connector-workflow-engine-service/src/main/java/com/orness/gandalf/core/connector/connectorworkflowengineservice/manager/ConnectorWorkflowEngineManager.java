package com.orness.gandalf.core.connector.connectorworkflowengineservice.manager;

import com.orness.gandalf.core.connector.connectorworkflowengineservice.communication.event.SubscriberWorkflowEngineZeroMQ;
import com.orness.gandalf.core.connector.connectorworkflowengineservice.config.TaskExecutorConfiguration;
import com.orness.gandalf.core.connector.connectorworkflowengineservice.workflow.ConnectorWorkflowEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class ConnectorWorkflowEngineManager {

    private ConnectorWorkflowEngine connectorWorkflowEngine;
    private BeanDefinitionRegistry beanDefinitionRegistry;

    @Autowired
    public ConnectorWorkflowEngineManager(ConnectorWorkflowEngine connectorWorkflowEngine, BeanDefinitionRegistry beanDefinitionRegistry) {
        this.connectorWorkflowEngine = connectorWorkflowEngine;
        this.beanDefinitionRegistry = beanDefinitionRegistry;
    }

    public void subscribeTopic(String topic) {
        String beanName = topic + "SubscriberWorkflowEngineZeroMQ";
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(SubscriberWorkflowEngineZeroMQ.class).setLazyInit(true);
        beanDefinitionRegistry.registerBeanDefinition(beanName, builder.getBeanDefinition());

        ApplicationContext context = new AnnotationConfigApplicationContext(TaskExecutorConfiguration.class);
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");

        SubscriberWorkflowEngineZeroMQ subscriberWorkflowEngineZeroMQ = (SubscriberWorkflowEngineZeroMQ) context.getBean(beanName);
        taskExecutor.execute(subscriberWorkflowEngineZeroMQ);
    }

    public void unsubscribeTopic(String topic) {
        String beanName = topic + "SubscriberWorkflowEngineZeroMQ";

        ApplicationContext context = new AnnotationConfigApplicationContext(TaskExecutorConfiguration.class);

        SubscriberWorkflowEngineZeroMQ subscriberWorkflowEngineZeroMQ = (SubscriberWorkflowEngineZeroMQ) context.getBean(beanName);
        subscriberWorkflowEngineZeroMQ.close();
    }

/*    public Iterator<MessageResponse> getMessageStream(Subscribe subscribe) {
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        Iterator<MessageResponse> messageResponseIterator = grpcBusJavaClient.getMessageStream(subscribe.getTopic(), subscribe.getSubscriber());

        grpcBusJavaClient.stopClient();
        return messageResponseIterator;
    }

    public void getMessageStreamWorkflow(Subscribe subscribe) {
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        Iterator<MessageResponse> messageResponseIterator = grpcBusJavaClient.getMessageStream(subscribe.getTopic(), subscribe.getSubscriber());
        while(messageResponseIterator.hasNext()) {
            com.orness.gandalf.core.module.connectorbusservice.grpc.Message currentMessage =  messageResponseIterator.next().getMessage();

            this.connectorWorkflowEngine.sendMessageWorkflowEngine(new MessageGandalf(currentMessage.getTopic(),
                    currentMessage.getSender(),
                    currentMessage.getExpirationTime(),
                    currentMessage.getCreationDate(),
                    currentMessage.getContent()));
        }
        grpcBusJavaClient.stopClient();
    }

    public void subscribeTopicBus(Subscribe subscribe) {

        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        grpcBusJavaClient.subscribeTopic(subscribe.getTopic(), subscribe.getSubscriber());
        grpcBusJavaClient.stopClient();
    }

    public void unsubscribeTopicBus(Subscribe subscribe) {
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        grpcBusJavaClient.unsubscribeTopic(subscribe.getTopic(), subscribe.getSubscriber());
        grpcBusJavaClient.stopClient();
    }*/
}
