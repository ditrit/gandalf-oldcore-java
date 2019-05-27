package com.orness.gandalf.core.test.javakafka1.sample;

import com.orness.gandalf.core.library.grpcjavaclient.bus.GrpcBusJavaClient;
import com.orness.gandalf.core.library.grpcjavaclient.workflowengine.GrpcWorkflowEngineJavaClient;
import com.orness.gandalf.core.module.messagebusmodule.domain.MessageBus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JavaKafka1Sample implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start sample");
        //test_asynch();
        test();
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

    public void test() {

        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        GrpcWorkflowEngineJavaClient grpcWorkflowEngineJavaClient = new GrpcWorkflowEngineJavaClient();

        //PARAM
        String name = "Java1";
        String listen_topic = "zeebeW2J";
        String send_topic = "zeebeJ2J";
        String content = "content";

        //GET
        System.out.println("subscribeOneTopic");
        MessageBus messageBus = grpcWorkflowEngineJavaClient.subscribeOneTopic(listen_topic, name);
        System.out.println(messageBus);

        //SEND
        System.out.println("createTopic");
        grpcBusJavaClient.createTopic(send_topic);
        System.out.println("sendMessage");
        grpcBusJavaClient.sendMessage(send_topic, name, content);
    }
}
