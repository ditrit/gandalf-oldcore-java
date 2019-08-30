package com.orness.gandalf.core.connector.connectorartifact.core;

import com.orness.gandalf.core.module.customartifactmodule.normative.worker.CustomArtifactCommonWorkerCommand;
import com.orness.gandalf.core.module.customartifactmodule.custom.worker.CustomArtifactSpecificWorkerCommand;
import com.orness.gandalf.core.module.gandalfmodule.worker.command.GandalfWorkerCommand;
import com.orness.gandalf.core.module.nexusmodule.normative.worker.NexusCommonWorkerCommand;
import com.orness.gandalf.core.module.nexusmodule.custom.worker.NexusSpecificWorkerCommand;
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
@ComponentScan(basePackages = {"com.orness.gandalf.core.module.gandalfmodule", "com.orness.gandalf.core.module.artifactmodule", "com.orness.gandalf.core.module.customartifactmodule"})
@Order
public class ConnectorArtifactCoreConfiguration {

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
            case "custom-artifact-module":
                commonWorkerCommand = (CustomArtifactCommonWorkerCommand) context.getBean("commonWorkerCommand");
                break;
            case "nexus-module":
                commonWorkerCommand = (NexusCommonWorkerCommand) context.getBean("commonWorkerCommand");
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
            case "custom-artifact-module":
                commonWorkerCommand = (CustomArtifactSpecificWorkerCommand) context.getBean("specificWorkerCommand");
                break;
            case "nexus-module":
                commonWorkerCommand = (NexusSpecificWorkerCommand) context.getBean("commonWorkerCommand");
                break;
            default:
                break;
        }
        taskExecutor.execute(commonWorkerCommand);
    }
}
