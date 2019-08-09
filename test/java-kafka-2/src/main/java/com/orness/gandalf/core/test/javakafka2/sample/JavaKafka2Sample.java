package com.orness.gandalf.core.test.javakafka2.sample;

import com.orness.gandalf.core.library.gandalfjavaclient.GandalfJavaClient;
import com.orness.gandalf.core.module.busmodule.core.properties.BusProperties;
import com.orness.gandalf.core.module.gandalfmodule.communication.event.GandalfPublisherEvent;
import com.orness.gandalf.core.module.kafkamodule.core.consumer.gandalf.KafkaGandalfEventConsumer;
import com.orness.gandalf.core.module.kafkamodule.core.properties.KafkaProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
@ComponentScan(basePackages = {"com.orness.gandalf.core.library.gandalfjavaclient", "com.orness.gandalf.core.module.gandalfmodule", "com.orness.gandalf.core.module.kafkamodule", "com.orness.gandalf.core.module.busmodule"})
public class JavaKafka2Sample implements CommandLineRunner {

    private GandalfJavaClient gandalfJavaClient;
    private GandalfSubscriber gandalfSubscriberEventService;
    private ApplicationContext context;
    private ThreadPoolTaskExecutor taskExecutor;
    private BusProperties busProperties;
    private KafkaProperties kafkaProperties;
    private GandalfPublisherEvent gandalfPublisherEvent;

    @Autowired
    public JavaKafka2Sample(GandalfJavaClient gandalfJavaClient, GandalfPublisherEvent gandalfPublisherEvent, GandalfSubscriber gandalfSubscriberEventService, ApplicationContext context, ThreadPoolTaskExecutor taskExecutor, BusProperties busProperties, KafkaProperties kafkaProperties) {
        this.gandalfJavaClient = gandalfJavaClient;
        this.gandalfSubscriberEventService = gandalfSubscriberEventService;
        this.gandalfPublisherEvent = gandalfPublisherEvent;
        this.context = context;
        this.taskExecutor = taskExecutor;
        this.kafkaProperties = kafkaProperties;
        this.busProperties = busProperties;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Start sample");
        test2();
    }

    public void test() {
        this.gandalfSubscriberEventService.startInstanceByTopic("test");
    }

    public void test2() {
        KafkaGandalfEventConsumer kafkaGandalfEventConsumer = new KafkaGandalfEventConsumer("test", this.gandalfPublisherEvent, kafkaProperties, busProperties);
        taskExecutor.execute(kafkaGandalfEventConsumer);
    }
}
