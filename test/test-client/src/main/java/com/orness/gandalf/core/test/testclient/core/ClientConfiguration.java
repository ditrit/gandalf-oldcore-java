package com.orness.gandalf.core.test.testclient.core;

import com.orness.gandalf.core.module.zeromqcore.command.client.ThreadClientZeroMQ;
import com.orness.gandalf.core.module.zeromqcore.command.listener.ThreadListenerCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqcore.event.client.PublisherZeroMQ;
import com.orness.gandalf.core.module.zeromqcore.event.listener.ThreadListenerEventZeroMQ;
import com.orness.gandalf.core.test.testclient.properties.GandalfProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.zeromq.ZMsg;

import java.util.List;


@Configuration
@ComponentScan(basePackages = {"com.orness.gandalf.core.module.clientcore"})
@Order
public class ClientConfiguration {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.";

    @Autowired
    private ApplicationContext context;

    @Value("${connector.name}")
    private String name;

    @Value("${" + PROPERTIES_BASE + "connectorCommandFrontEndConnection}")
    private List<String> clientFrontEndCommandConnection;

    @Value("${" + PROPERTIES_BASE + "connectorEventFrontEndConnection}")
    private String clientFrontEndEventConnection;

    @Value("${" + PROPERTIES_BASE + "connectorCommandBackEndConnection}")
    private List<String> listenerBackEndCommandConnection;

    @Value("${" + PROPERTIES_BASE + "connectorEventBackEndConnection}")
    private String listenerBackEndEventConnection;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(10);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Bean
    public ThreadClientZeroMQ connectorCommandClient() {

        return new ThreadClientZeroMQ(name, this.clientFrontEndCommandConnection);
    }

    @Bean
    public PublisherZeroMQ connectorEventClient() {

        return new PublisherZeroMQ(name, this.clientFrontEndEventConnection);
    }

    @Bean
    public ThreadListenerCommandZeroMQ connectorCommandListener() {

        return new ThreadListenerCommandZeroMQ(name, this.listenerBackEndCommandConnection);
    }

    @Bean
    public ThreadListenerEventZeroMQ connectorEventListener() {

        return new ThreadListenerEventZeroMQ(name, this.listenerBackEndEventConnection);
    }

    @Bean
    public void gandalfLoop() {
        ThreadListenerCommandZeroMQ command = this.connectorCommandListener();
        command.start();
        ThreadListenerEventZeroMQ event = this.connectorEventListener();
        event.start();
        while(true) {
            ZMsg commandMsg = command.getCommandAsync();
            ZMsg eventMsg = event.getEventAsync();
            if(commandMsg != null) {
                System.out.println(commandMsg);
                command.sendResultCommand(commandMsg, true);
            }
            if(eventMsg != null) {
                System.out.println(eventMsg);
            }
        }
    }


/*
    @Bean
    public void gandalfLoop() {
        this.taskExecutor().execute(this.gandalfClient);
        while(true) {
            //System.out.println("Sending test/PrintTest/TEST");
            //this.gandalfPublisher.sendEvent("test", "PrintTest", "TEST");
            System.out.println("Sending test/TestWorker/ADMIN/PrintTest/Client");
            this.gandalfClient.sendCommand("test", "TestWorker", WORKER_SERVICE_CLASS_ADMIN, "PrintTest", "Client");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("LAST REP");
            System.out.println(this.gandalfClient.getLastReponses());
        }
    }*/
}
