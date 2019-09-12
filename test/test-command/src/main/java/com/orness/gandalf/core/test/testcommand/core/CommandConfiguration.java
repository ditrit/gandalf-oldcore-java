package com.orness.gandalf.core.test.testcommand.core;

import com.orness.gandalf.core.module.zeromqcore.command.client.ThreadClientZeroMQ;
import com.orness.gandalf.core.module.zeromqcore.command.listener.ThreadListenerCommandZeroMQ;
import com.orness.gandalf.core.module.zeromqcore.event.client.PublisherZeroMQ;
import com.orness.gandalf.core.module.zeromqcore.event.listener.ThreadListenerEventZeroMQ;
import com.orness.gandalf.core.test.testcommand.properties.GandalfProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.zeromq.ZMsg;

import java.util.List;

@Configuration
@Order
public class CommandConfiguration {

    private static final String PROPERTIES_BASE = "${instance.name}.connectors.${connector.type}.${connector.name}.";


    @Value("${connector.name}")
    private String name;

    @Autowired
    private ApplicationContext context;

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
        ThreadClientZeroMQ command = this.connectorCommandClient();
        PublisherZeroMQ event = this.connectorEventClient();
        while(true) {
            event.sendEvent("toto", "toto", "toto");
            command.sendCommandAsync("toto", "connector_client_test", "toto", "toto", "toto");
            ZMsg commandResponse = command.getResponseAsync();
            if(commandResponse != null) {
                System.out.println(commandResponse);
            }
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
