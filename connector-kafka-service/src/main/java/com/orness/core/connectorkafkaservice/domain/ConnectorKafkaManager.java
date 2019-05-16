package com.orness.core.connectorkafkaservice.domain;

import com.orness.core.connectorkafkaservice.consumer.ConnectorKafkaListener;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.GenericWebApplicationContext;

@Component
public class ConnectorKafkaManager {

    private GenericWebApplicationContext applicationContext;

    @Autowired
    public ConnectorKafkaManager(GenericWebApplicationContext  applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void addTopicKafkaAndTopicKafkaListener(String topic) {
        this.addTopicKafka(topic);
        this.addTopicKafkaListener(topic);
    }

    private void addTopicKafkaListener(String topic) {
        applicationContext.registerBean(topic+"KafkaListenerBean", ConnectorKafkaListener.class, () -> new ConnectorKafkaListener(topic));
    }

    private void addTopicKafka(String topic) {
        applicationContext.registerBean(topic, NewTopic.class, () -> new NewTopic(topic,1, (short) 1));
    }

    @Bean
    public ConnectorKafkaListener connectorMessageKafkaListenerW2W() {
        return new ConnectorKafkaListener("zeebeW2W");
    }

    @Bean
    public ConnectorKafkaListener connectorMessageKafkaListenerJ2W() {
        return new ConnectorKafkaListener("zeebeJ2W");
    }

/*    private CountDownLatch latch = new CountDownLatch(3);
    private ZeebeClient zeebeClient;

    @Autowired
    public ConnectorKafkaManager(ZeebeClient zeebeClient) {
        this.zeebeClient = zeebeClient;
    }

    //WORKFLOW TO WORKFLOW
    @KafkaListener(topics = "zeebeW2W", groupId = "kafkamessage", containerFactory = "connectorMessageKafkaListenerContainerFactory")
    public void connectorMessageKafkaListenerW2W(MessageKafka messageKafka) {
        System.out.println("Received kafkaMessage message: " + messageKafka.toString());
        this.latch.countDown();

        zeebeClient.newPublishMessageCommand()
                .messageName("message")
                .correlationKey(messageKafka.getWorkflow_content())
                //.variables()
                .timeToLive(Duration.ofMinutes(1))
                .send().join();
    }

    //TODO REGROUP LISTENER ??
    //JAVA TO WORKFLOW
    @KafkaListener(topics = "zeebeJ2W", groupId = "kafkamessage", containerFactory = "connectorMessageKafkaListenerContainerFactory")
    public void connectorMessageKafkaListenerJ2W(MessageKafka messageKafka) {
        System.out.println("Received kafkaMessage message: " + messageKafka.toString());
        this.latch.countDown();

        zeebeClient.newPublishMessageCommand()
                .messageName("message")
                .correlationKey(messageKafka.getWorkflow_content())
                //.variables()
                .timeToLive(Duration.ofMinutes(1))
                .send().join();
    }*/

}

