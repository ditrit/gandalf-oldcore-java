package com.orness.gandalf.core.test.javakafka2.sample;

import com.orness.gandalf.core.library.zeromqjavaclient.ZeroMQJavaClient;
import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class JavaKafka2Sample implements CommandLineRunner {

    @Value("${gandalf.communication.worker}")
    private String connectionWorker;

    @Value("${gandalf.communication.subscriber}")
    private String connectionSubscriber;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start sample");
        //testZeroMQSender();
        //testMultipleTopics(5);
        testPerformanceLoop(100, false);
        //test_asynch();
        //testMultipleMessage(10);
        //test();
    }

    public void testPerformanceLoop(int numberIteration, boolean multipleTopic) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ThreadConfig.class);
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        for(int indice = 0; indice < numberIteration; indice++) {
            int current_indice = indice;
            taskExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    testPerformance(current_indice, multipleTopic);
                }
            });
        }
    }

    public void testPerformance(int indice, boolean multipleTopic) {

        ZeroMQJavaClient zeroMQJavaClient = new ZeroMQJavaClient(connectionWorker, connectionSubscriber);

        //PARAM
        String name = "JavaJ2J_"+indice;
        String listen_topic = topicMultiple("zeebeJ2J", multipleTopic, indice);
        String send_topic = topicMultiple("zeebeJ2W", multipleTopic, indice);
        String content = "content_"+name+"_"+indice;

        //GET
        System.out.println("subscribeOneTopic");
        MessageGandalf message = zeroMQJavaClient.getMessageSubscriberBusTopic(listen_topic);
        System.out.println(message);

        //SEND
        System.out.println("createTopic");
        zeroMQJavaClient.createTopic(send_topic);
        System.out.println("sendMessage");
        zeroMQJavaClient.sendMessageTopic(send_topic, content);
    }

    private String topicMultiple(String topic, boolean multipleTopic, int indice) {
        if(multipleTopic) {
            return topic + "_" + indice;
        }
        else {
            return topic;
        }
    }

    /*public void test() {
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        GrpcWorkflowEngineJavaClient grpcWorkflowEngineJavaClient = new GrpcWorkflowEngineJavaClient();

        //PARAM
        String name = "Java2";
        String listen_topic = "zeebeJ2J";
        String send_topic = "zeebeJ2W";
        String content = "content";

        //GET
        System.out.println("subscribeOneTopic");
        MessageGandalf message = grpcWorkflowEngineJavaClient.subscribeOneTopic(listen_topic, name);
        System.out.println(message);
        grpcWorkflowEngineJavaClient.stopClient();
        //SEND
        System.out.println("createTopic");
        grpcBusJavaClient.createTopic(send_topic);
        System.out.println("sendMessage");
        grpcBusJavaClient.sendMessage(send_topic, name, content);
        grpcBusJavaClient.stopClient();
    }

    public void testMultipleMessage(int number) {
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();

        String name = "tata";
        String topic = "zeebeW2J";
        for(int i=0; i < number ; i++) {
            System.out.println("sendMessage");
            grpcBusJavaClient.sendMessage(topic, name, name);
        }
    }

    public void testMultipleTopics(int number) {
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();

        String name = "tata";
        String topic = "toto";
        for(int i=0; i < number ; i++) {
            System.out.println("sendMessage");
            String topic_indice = topic + "_" + i;
            grpcBusJavaClient.sendMessage(topic_indice, name, name);
        }
    }

    public void test_asynch() {
        GrpcWorkflowEngineJavaClient grpcWorkflowEngineJavaClient = new GrpcWorkflowEngineJavaClient();

        //PARAM
        String name_1 = "tata_subscriber";
        String topic_1 = "tata_topic";
        String name_2 = "toto_subscriber";
        String topic_2 = "toto_topic";

        grpcWorkflowEngineJavaClient.subscribeTopic(topic_1, name_1);
        grpcWorkflowEngineJavaClient.subscribeTopic(topic_2, name_2);
    }

    public void testZeroMQSender() {
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        int number = 5;
        String name = "tata";
        String topic = "toto";
        for(int i=0; i < number ; i++) {
            System.out.println("sendMessage");
            grpcBusJavaClient.sendMessage(topic, name, name+"_"+i);
        }
    }*/
}
