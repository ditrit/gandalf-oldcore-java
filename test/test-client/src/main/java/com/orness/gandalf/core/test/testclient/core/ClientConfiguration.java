package com.orness.gandalf.core.test.testclient.core;

import com.orness.gandalf.core.test.testzeromq.command.Client;
import com.orness.gandalf.core.test.testzeromq.gandalf.GandalfClient;
import com.orness.gandalf.core.test.testzeromq.gandalf.GandalfProperties;
import com.orness.gandalf.core.test.testzeromq.gandalf.GandalfPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import static com.orness.gandalf.core.test.testzeromq.Constant.*;

@Configuration
@Order
public class ClientConfiguration {

    private GandalfProperties gandalfProperties;
    private GandalfPublisher gandalfPublisher;
    private GandalfClient gandalfClient;

    @Autowired
    public ClientConfiguration(GandalfProperties gandalfProperties, GandalfPublisher gandalfPublisher, GandalfClient gandalfClient) {
        this.gandalfProperties = gandalfProperties;
        this.gandalfPublisher = gandalfPublisher;
        this.gandalfClient = gandalfClient;
    }

    @Bean
    public void gandalfPublisherLoop() {
        while(true) {
            this.gandalfPublisher.sendEvent("test", "PrintTest", "TEST");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Bean
    public void gandalfClientLoop() {
        while(true) {
            this.gandalfClient.sendCommand("test", "TestWorker", WORKER_SERVICE_CLASS_ADMIN, "PrintTest", "Client");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}