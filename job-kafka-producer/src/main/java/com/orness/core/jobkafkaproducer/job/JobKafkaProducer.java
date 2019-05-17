package com.orness.core.jobkafkaproducer.job;

import com.orness.core.connectorbusservice.domain.ConnectorKafkaServiceGrpc;
import com.orness.core.connectorbusservice.domain.MessageRequest;
import com.orness.core.connectorbusservice.domain.MessageResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.zeebe.client.ZeebeClient;
import io.zeebe.client.api.clients.JobClient;
import io.zeebe.client.api.response.ActivatedJob;
import io.zeebe.client.api.subscription.JobHandler;
import io.zeebe.client.api.subscription.JobWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;
import java.util.Map;

@Component
public class JobKafkaProducer implements JobHandler {

    private ZeebeClient zeebe;
    private JobWorker subscription;

    @Autowired
    public JobKafkaProducer(ZeebeClient zeebe) {
        this.zeebe = zeebe;
    }

    @PostConstruct
    public void subscribe() {
        subscription = zeebe.newWorker()
                .jobType("kafka_producer")
                .handler(this)
                .timeout(Duration.ofMinutes(10))
                .open();
    }

    @PreDestroy
    public void closeSubscription() {
        subscription.close();
    }

    @Override
    public void handle(JobClient jobClient, ActivatedJob activatedJob) {

        //Get workflow variables
        Map<String, Object> workflow_variables = activatedJob.getVariablesAsMap();

        //Send message to ConnectorKafka
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 10000).usePlaintext().build();
        ConnectorKafkaServiceGrpc.ConnectorKafkaServiceBlockingStub stub = ConnectorKafkaServiceGrpc.newBlockingStub(channel);
        System.out.println(workflow_variables);
        MessageResponse messageResponse = stub.message(MessageRequest.newBuilder()
                .setId(workflow_variables.get("workflow_id").toString())
                .setName(workflow_variables.get("workflow_name").toString())
                .setTopic(workflow_variables.get("workflow_topic").toString())
                .setContent(workflow_variables.get("workflow_content").toString())
                .build());

        System.out.println("Response received from server:\n" + messageResponse);
        channel.shutdown();

        //Send job complete command
        jobClient.newCompleteCommand(activatedJob.getKey()).variables(workflow_variables).send().join();
    }
}
