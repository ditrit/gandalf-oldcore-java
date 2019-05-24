package com.orness.gandalf.core.javakafka2.sample;


import com.orness.gandalf.core.grpcjavaclient.workflowengine.GrpcWorkflowEngineJavaClient;
import com.orness.gandalf.core.grpcjavaclient.bus.GrpcBusJavaClient;
import com.orness.gandalf.core.messagebusmodule.domain.MessageBus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class JavaKafka2Sample implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start sample");
        //test_asynch();
        test();
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

    public void test() {
        GrpcBusJavaClient grpcBusJavaClient = new GrpcBusJavaClient();
        GrpcWorkflowEngineJavaClient grpcWorkflowEngineJavaClient = new GrpcWorkflowEngineJavaClient();

        //PARAM
        String name = "Java2";
        String listen_topic = "zeebeJ2J";
        String send_topic = "zeebeJ2W";
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
