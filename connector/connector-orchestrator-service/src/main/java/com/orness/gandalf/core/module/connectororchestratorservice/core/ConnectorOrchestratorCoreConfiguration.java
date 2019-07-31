package com.orness.gandalf.core.module.connectororchestratorservice.core;

import com.orness.gandalf.core.module.gandalfmodule.communication.command.GandalfWorkerCommand;
import com.orness.gandalf.core.module.zeromqmodule.command.worker.RunnableWorkerZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ConnectorOrchestratorCoreConfiguration {

    @Autowired
    private ApplicationContext context;

    @Value("spring.profiles.active")
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
    public void gandalfWorkerCommand() {
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
    public void commonWorkerCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        RunnableWorkerZeroMQ commonWorkerCommand = null;
        //TODO PROPERTIES

        switch(profile) {
            case "custom-orchestrator-module":
                //commonWorkerCommand = (H2Com) context.getBean("commonWorkerCommand");
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
    public void specificWorkerCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        RunnableWorkerZeroMQ commonWorkerCommand = null;
        //TODO PROPERTIES

        switch(profile) {
            case "custom-orchestrator-module":
                //commonWorkerCommand = (CustomOrches) context.getBean("specificWorkerCommand");
                break;
            default:
                break;
        }
        taskExecutor.execute(commonWorkerCommand);
    }
}
