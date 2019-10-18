package com.ditrit.gandalf.worker.workergandalf.initialization;

import com.ditrit.gandalf.core.clientcore.properties.ClientProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.zeromq.ZMsg;
import properties.WorkerProperties;
import service.WorkerClientService;

@Configuration
@ComponentScan(basePackages = {"com.ditrit.gandalf.core.workercore", "com.ditrit.gandalf.coreclientcore"})
public class WorkerGandalfInitialization {

    //@Value("${${instance.name}.connectors.${connector.type}.${connector.name}.target.type}")
    //private String targetType;

    private ApplicationContext context;
    private WorkerProperties workerProperties;
    private ClientProperties clientProperties;

    @Autowired
    public WorkerGandalfInitialization(ApplicationContext context, WorkerProperties workerProperties, ClientProperties clientProperties) {
        this.context = context;
        this.workerProperties = workerProperties;
        this.clientProperties = clientProperties;
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
    public void gandalfWorkerClientService() {
        WorkerClientService gandalfWorkerClientService = (WorkerClientService) context.getBean("workerClientService");
        if(gandalfWorkerClientService != null) {
            this.taskExecutor().execute(gandalfWorkerClientService);
            this.initialize(gandalfWorkerClientService);
        }
    }

    private void initialize(WorkerClientService workerClientService) {
        ZMsg response = workerClientService.sendRequest("configuration");
        Object[] responseConnections =  response.toArray();

        this.workerProperties.setWorkerCommandFrontEndReceiveConnection(responseConnections[0].toString());
        this.workerProperties.setWorkerEventFrontEndReceiveConnection(responseConnections[1].toString());

        this.clientProperties.setConnectorCommandBackEndSendConnection(responseConnections[2].toString());
        this.clientProperties.setConnectorEventBackEndSendConnection(responseConnections[3].toString());
    }
}
