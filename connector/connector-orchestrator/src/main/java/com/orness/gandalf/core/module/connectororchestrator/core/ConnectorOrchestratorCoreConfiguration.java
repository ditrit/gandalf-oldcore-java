package com.orness.gandalf.core.module.connectororchestrator.core;

import com.orness.gandalf.core.module.customorchestratormodule.normative.worker.CustomOrchestratorCommonWorkerCommand;
import com.orness.gandalf.core.module.customorchestratormodule.custom.worker.CustomOrchestratorSpecificWorker;
import com.orness.gandalf.core.module.gandalfmodule.worker.command.GandalfWorkerCommand;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
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

    //                            _  .-')     ('-.
    //                       ( \( -O )  _(  OO)
    //   .-----.  .-'),-----. ,------. (,------.
    //  '  .--./ ( OO'  .-.  '|   /`. ' |  .---'
    //  |  |('-. /   |  | |  ||  /  | | |  |
    // /_) |OO  )\_) |  |\|  ||  |_.' |(|  '--.
    // ||  |`-'|   \ |  | |  ||  .  '.' |  .--'
    //(_'  '--'\    `'  '-'  '|  |\  \  |  `---.
    //   `-----'      `-----' `--' '--' `------'

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(10);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }


    //                   ('-.         .-') _  _ .-') _     ('-.
    //              ( OO ).-.    ( OO ) )( (  OO) )   ( OO ).-.
    //  ,----.      / . --. /,--./ ,--,'  \     .'_   / . --. / ,--.        ,------.
    // '  .-./-')   | \-.  \ |   \ |  |\  ,`'--..._)  | \-.  \  |  |.-') ('-| _.---'
    // |  |_( O- ).-'-'  |  ||    \|  | ) |  |  \  '.-'-'  |  | |  | OO )(OO|(_\
    // |  | .--, \ \| |_.'  ||  .     |/  |  |   ' | \| |_.'  | |  |`-' |/  |  '--.
    //(|  | '. (_/  |  .-.  ||  |\    |   |  |   / :  |  .-.  |(|  '---.'\_)|  .--'
    // |  '--'  |   |  | |  ||  | \   |   |  '--'  /  |  | |  | |      |   \|  |_)
    //  `------'    `--' `--'`--'  `--'   `-------'   `--' `--' `------'    `--'

    @Bean
    public void connectorGandalfWorkerCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        RunnableWorkerZeroMQ gandalfWorkerCommand = (GandalfWorkerCommand) context.getBean("gandalfWorkerCommand");
        taskExecutor.execute(gandalfWorkerCommand);
    }

    //                        _   .-')    _   .-')                     .-') _
    //                       ( '.( OO )_ ( '.( OO )_                  ( OO ) )
    //   .-----.  .-'),-----. ,--.   ,--.),--.   ,--.).-'),-----. ,--./ ,--,'
    //  '  .--./ ( OO'  .-.  '|   `.'   | |   `.'   |( OO'  .-.  '|   \ |  |\
    //  |  |('-. /   |  | |  ||         | |         |/   |  | |  ||    \|  | )
    // /_) |OO  )\_) |  |\|  ||  |'.'|  | |  |'.'|  |\_) |  |\|  ||  .     |/
    // ||  |`-'|   \ |  | |  ||  |   |  | |  |   |  |  \ |  | |  ||  |\    |
    //(_'  '--'\    `'  '-'  '|  |   |  | |  |   |  |   `'  '-'  '|  | \   |
    //   `-----'      `-----' `--'   `--' `--'   `--'     `-----' `--'  `--'

    @Bean
    public void connectorCommonWorkerCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        RunnableWorkerZeroMQ commonWorkerCommand = null;

        switch(profile) {
            case "custom-orchestrator-module":
                commonWorkerCommand = (CustomOrchestratorCommonWorkerCommand) context.getBean("commonWorkerCommand");
                break;
            default:
                break;
        }
        taskExecutor.execute(commonWorkerCommand);
    }

    //      .-')     _ (`-.    ('-.
    // ( OO ).  ( (OO  ) _(  OO)
    //(_)---\_)_.`     \(,------.   .-----.  ,-.-')    ,------.,-.-')   .-----.
    ///    _ |(__...--'' |  .---'  '  .--./  |  |OO)('-| _.---'|  |OO) '  .--./
    //\  :` `. |  /  | | |  |      |  |('-.  |  |  \(OO|(_\    |  |  \ |  |('-.
    // '..`''.)|  |_.' |(|  '--.  /_) |OO  ) |  |(_//  |  '--. |  |(_//_) |OO  )
    //.-._)   \|  .___.' |  .--'  ||  |`-'| ,|  |_.'\_)|  .--',|  |_.'||  |`-'|
    //\       /|  |      |  `---.(_'  '--'\(_|  |     \|  |_)(_|  |  (_'  '--'\
    // `-----' `--'      `------'   `-----'  `--'      `--'    `--'     `-----'

    @Bean
    public void connectorSpecificWorkerCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        RunnableWorkerZeroMQ commonWorkerCommand = null;

        switch(profile) {
            case "custom-orchestrator-module":
                commonWorkerCommand = (CustomOrchestratorSpecificWorker) context.getBean("specificWorkerCommand");
                break;
            default:
                break;
        }
        taskExecutor.execute(commonWorkerCommand);
    }
}