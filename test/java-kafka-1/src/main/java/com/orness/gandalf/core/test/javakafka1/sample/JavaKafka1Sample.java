package com.orness.gandalf.core.test.javakafka1.sample;

import com.orness.gandalf.core.library.grpcjavaclient.bus.GrpcBusJavaClient;
import com.orness.gandalf.core.library.grpcjavaclient.workflowengine.GrpcWorkflowEngineJavaClient;
import com.orness.gandalf.core.module.connectorworkflowengineservice.grpc.MessageResponse;
import com.orness.gandalf.core.module.messagemodule.domain.MessageGandalf;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class JavaKafka1Sample implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start sample");
        //testMultipleTopics();
        testPerformanceLoop(10, false);
        //test_asynch();
        //testMultipleMessage();
        //test();
        //testZeroMQSubscriber();
    }

    public void testPerformanceLoop(int numberIteration, boolean multipleTopic) {
        for(int indice = 0; indice < numberIteration; indice++) {
            testPerformance(indice, multipleTopic);
        }
    }

    public void testPerformance(int indice, boolean multipleTopic) {

        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        GrpcWorkflowEngineJavaClient grpcWorkflowEngineJavaClient = new GrpcWorkflowEngineJavaClient();

        //PARAM
        String name = "JavaW2J_"+indice;
        String listen_topic = topicMultiple("zeebeW2J", multipleTopic, indice);
        String send_topic = topicMultiple("zeebeJ2J", multipleTopic, indice);
        String content = "content_"+name+"_"+indice;

        //GET
        System.out.println("subscribeOneTopic");
        MessageGandalf message = grpcWorkflowEngineJavaClient.subscribeOneTopic(listen_topic, name);
        System.out.println(message);

        //SEND
        System.out.println("createTopic");
        grpcBusJavaClient.createTopic(send_topic);
        System.out.println("sendMessage");
        grpcBusJavaClient.sendMessage(send_topic, name, content);
    }

    private String topicMultiple(String topic, boolean multipleTopic, int indice) {
        if(multipleTopic) {
            return topic + "_" + indice;
        }
        else {
            return topic;
        }
    }

    public void test_asynch() {
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        GrpcWorkflowEngineJavaClient grpcWorkflowEngineJavaClient = new GrpcWorkflowEngineJavaClient();

        //PARAM
        String name_1 = "tata_sender";
        String topic_1 = "tata_topic";
        String name_2 = "toto_sender";
        String topic_2 = "toto_topic";

        System.out.println("1");
        grpcBusJavaClient.createTopic(topic_1);
        System.out.println("2");
        grpcBusJavaClient.createTopic(topic_2);

        for(int indice =0 ; indice < 5 ; indice++) {
            grpcBusJavaClient.sendMessage(topic_1, name_1, "content_"+indice);
            grpcBusJavaClient.sendMessage(topic_2, name_2, "content_"+indice);
        }
    }

    public void testMultipleMessage() {

        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        GrpcWorkflowEngineJavaClient grpcWorkflowEngineJavaClient = new GrpcWorkflowEngineJavaClient();

        //PARAM
        String name = "toto";
        String topic = "toto";

        //SEND
        System.out.println("createTopic");
        grpcBusJavaClient.createTopic(topic);
        System.out.println("subscribe");
        grpcBusJavaClient.subscribeTopic(topic, name);
        //System.out.println("getMessage");
        //System.out.println(grpcBusJavaClient.getMessage(topic, name));
        System.out.println("getMessageStream");
        Iterator ite = grpcBusJavaClient.getMessageStream(topic, name);
        while(ite.hasNext()) {
            System.out.println(ite.next());
        }
    }

    public void testMultipleTopics() {

        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        GrpcWorkflowEngineJavaClient grpcWorkflowEngineJavaClient = new GrpcWorkflowEngineJavaClient();

        //PARAM
        String name = "toto";
        String topic = "toto";
        int number = 5;
        for(int i=0; i < number ; i++) {
            //SEND
            String topic_indice = topic+"_"+i;
            System.out.println("createTopic " + topic_indice);
            grpcBusJavaClient.createTopic(topic_indice);
            System.out.println("subscribe one " + topic_indice);
            System.out.println("Message " + grpcWorkflowEngineJavaClient.subscribeOneTopic(topic_indice, name));
        }
    }

    public void test() {

        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        GrpcWorkflowEngineJavaClient grpcWorkflowEngineJavaClient = new GrpcWorkflowEngineJavaClient();

        //PARAM
        String name = "Java";
        String listen_topic = "zeebeW2J";
        String send_topic = "zeebeJ2J";
        String content = "content";

        //GET
        System.out.println("subscribeOneTopic");
        MessageGandalf message = grpcWorkflowEngineJavaClient.subscribeOneTopic(listen_topic, name);
        System.out.println(message);

        //SEND
        System.out.println("createTopic");
        grpcBusJavaClient.createTopic(send_topic);
        System.out.println("sendMessage");
        grpcBusJavaClient.sendMessage(send_topic, name, content);
    }

    public void testZeroMQSubscriber() {
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        int number = 5;
        String name = "toto";
        String topic = "toto";
        grpcBusJavaClient.createTopic(name);

        GrpcWorkflowEngineJavaClient grpcWorkflowEngineJavaClient = new GrpcWorkflowEngineJavaClient();
        //TEST ONE
        //MessageGandalf messageGandalf = grpcWorkflowEngineJavaClient.subscribeOneTopic(topic, name);
        //System.out.println(messageGandalf);

        //TEST MULTIPLE
        Iterator<MessageResponse> iterator = grpcWorkflowEngineJavaClient.subscribeTopic(topic, name);
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getMessage());
        }

    }
}
