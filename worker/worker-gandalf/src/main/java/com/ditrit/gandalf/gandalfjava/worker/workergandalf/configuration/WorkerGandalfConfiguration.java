package com.ditrit.gandalf.gandalfjava.worker.workergandalf.configuration;

import com.ditrit.gandalf.gandalfjava.core.clientcore.client.ClientCommand;
import com.ditrit.gandalf.gandalfjava.core.clientcore.client.ClientEvent;
import com.ditrit.gandalf.gandalfjava.core.clientcore.properties.ClientProperties;
import com.ditrit.gandalf.gandalfjava.core.listenercore.listener.ListenerCommand;
import com.ditrit.gandalf.gandalfjava.core.listenercore.listener.ListenerEvent;
import com.ditrit.gandalf.gandalfjava.core.workercore.function.WorkerFunctionsService;
import com.ditrit.gandalf.gandalfjava.core.workercore.loader.WorkerJarFileLoaderService;
import com.ditrit.gandalf.gandalfjava.core.workercore.properties.WorkerProperties;
import com.ditrit.gandalf.gandalfjava.core.workercore.worker.Worker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
@Configuration
@ComponentScan(basePackages = {"com.ditrit.gandalf.gandalfjava.core.workercore", "com.ditrit.gandalf.gandalfjava.core.clientcore"})
@Order
public class WorkerGandalfConfiguration {

    private ApplicationContext context;
    private ClientProperties clientProperties;
    private WorkerProperties workerProperties;
    private WorkerJarFileLoaderService workerJarFileLoaderService;
    private WorkerFunctionsService workerFunctionsService;

    @Autowired
    public WorkerGandalfConfiguration(ApplicationContext context, ClientProperties clientProperties, WorkerProperties workerProperties, WorkerJarFileLoaderService workerJarFileLoaderService, WorkerFunctionsService workerFunctionsService) {
        this.context = context;
        this.clientProperties = clientProperties;
        this.workerProperties = workerProperties;
        this.workerFunctionsService = workerFunctionsService;
        this.workerJarFileLoaderService = workerJarFileLoaderService;
    }

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(10);
        pool.setMaxPoolSize(20);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Bean
    public void gandalfWorker() {
        Worker gandalfWorker = (Worker) context.getBean("worker");
        if(gandalfWorker != null) {
            this.taskExecutor().execute(gandalfWorker);
        }
    }

    @Bean
    public void gandalfClientCommand() {
        ClientCommand gandalfClientCommand = (ClientCommand) context.getBean("clientCommand");
        if(gandalfClientCommand != null) {
            this.taskExecutor().execute(gandalfClientCommand);
        }
    }

    @Bean
    public void gandalfClientEvent() {
        ClientEvent gandalfConnectorEvent = (ClientEvent) context.getBean("clientEvent");
    }

    @Bean
    public void gandalfListenerCommand() {
        ListenerCommand gandalfListenerCommand = (ListenerCommand) context.getBean("listenerCommand");
        if(gandalfListenerCommand != null) {
            this.taskExecutor().execute(gandalfListenerCommand);
        }
    }

    @Bean
    public void gandalfListenerEvent() {
        ListenerEvent gandalfListenerEvent = (ListenerEvent) context.getBean("listenerEvent");
        if(gandalfListenerEvent != null) {
            this.taskExecutor().execute(gandalfListenerEvent);
        }
    }
}
