package com.orness.gandalf.core.connector.connectorgandalfservice.core;

import com.orness.gandalf.core.connector.connectorgandalfservice.broker.GandalfBrokerCommandBean;
import com.orness.gandalf.core.connector.connectorgandalfservice.proxy.GandalfProxyEventBean;
import com.orness.gandalf.core.module.gandalfmodule.worker.command.GandalfWorkerCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@ComponentScan(basePackages = {"com.orness.gandalf.core.module.gandalfmodule"})
@Order
public class ConnectorGandalfConfiguration {

    @Autowired
    private ApplicationContext context;

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
        taskExecutor.execute((GandalfWorkerCommand) context.getBean("gandalfWorkerCommand"));
    }

    //    .-. .-')  _  .-')               .-. .-')     ('-.  _  .-')
    //\  ( OO )( \( -O )              \  ( OO )  _(  OO)( \( -O )
    // ;-----.\ ,------.  .-'),-----. ,--. ,--. (,------.,------.
    // | .-.  | |   /`. '( OO'  .-.  '|  .'   /  |  .---'|   /`. '
    // | '-' /_)|  /  | |/   |  | |  ||      /,  |  |    |  /  | |
    // | .-. `. |  |_.' |\_) |  |\|  ||     ' _)(|  '--. |  |_.' |
    // | |  \  ||  .  '.'  \ |  | |  ||  .   \   |  .--' |  .  '.'
    // | '--'  /|  |\  \    `'  '-'  '|  |\   \  |  `---.|  |\  \
    // `------' `--' '--'     `-----' `--' '--'  `------'`--' '--'

    @Bean
    public void brokerCommand() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        GandalfBrokerCommandBean gandalfBrokerCommandBean = (GandalfBrokerCommandBean) context.getBean("gandalfBrokerCommandBean");
        taskExecutor.execute(gandalfBrokerCommandBean);
    }

    //       _ (`-.  _  .-')              ) (`-.
    //  ( (OO  )( \( -O )              ( OO ).
    // _.`     \ ,------.  .-'),-----.(_/.  \_)-. ,--.   ,--.
    //(__...--'' |   /`. '( OO'  .-.  '\  `.'  /   \  `.'  /
    // |  /  | | |  /  | |/   |  | |  | \     /\ .-')     /
    // |  |_.' | |  |_.' |\_) |  |\|  |  \   \ |(OO  \   /
    // |  .___.' |  .  '.'  \ |  | |  | .'    \_)|   /  /\_
    // |  |      |  |\  \    `'  '-'  '/  .'.  \ `-./  /.__)
    // `--'      `--' '--'     `-----''--'   '--'  `--'

    @Bean
    public void proxyEvent() {
        ThreadPoolTaskExecutor taskExecutor = (ThreadPoolTaskExecutor) context.getBean("taskExecutor");
        GandalfProxyEventBean gandalfProxyEventBean = (GandalfProxyEventBean) context.getBean("gandalfProxyEventBean");
        taskExecutor.execute(gandalfProxyEventBean);
    }
}
