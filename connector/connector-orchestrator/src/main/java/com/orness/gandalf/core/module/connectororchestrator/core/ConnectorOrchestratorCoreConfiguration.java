package com.orness.gandalf.core.module.connectororchestrator.core;

import com.orness.gandalf.core.module.customorchestratormodule.custom.worker.ConnectorCustomOrchestratorCustomWorker;
import com.orness.gandalf.core.module.customorchestratormodule.normative.worker.ConnectorCustomOrchestratorNormativeWorker;
import com.orness.gandalf.core.module.gandalfmodule.worker.ConnectorGandalfWorker;
import com.orness.gandalf.core.module.zeromqcore.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan(basePackages = {"com.orness.gandalf.core.module.gandalfmodule", "com.orness.gandalf.core.module.orchestratormodule", "com.orness.gandalf.core.module.customorchestratormodule"})
@Order
public class ConnectorOrchestratorCoreConfiguration {

    @Autowired
    private ApplicationContext context;

    @Value("${spring.profiles.active}")
    private String profile;

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(10);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Bean
    public void connectorGandalfWorker() {
        RunnableWorkerZeroMQ gandalfWorkerCommand = (ConnectorGandalfWorker) context.getBean("gandalfWorker");
        this.taskExecutor().execute(gandalfWorkerCommand);
    }

    @Bean
    public void connectorNormativeWorker() {
        RunnableWorkerZeroMQ normativeWorker = null;
        switch(profile) {
            case "custom-orchestrator":
                normativeWorker = (ConnectorCustomOrchestratorNormativeWorker) context.getBean("normativeWorker");
                break;
            default:
                break;
        }
        this.taskExecutor().execute(normativeWorker);
    }

    @Bean
    public void connectorCustomWorker() {
        RunnableWorkerZeroMQ cutomWorker = null;
        switch(profile) {
            case "custom-orchestrator":
                cutomWorker = (ConnectorCustomOrchestratorCustomWorker) context.getBean("cutomWorker");
                break;
            default:
                break;
        }
        this.taskExecutor().execute(cutomWorker);
    }
}
