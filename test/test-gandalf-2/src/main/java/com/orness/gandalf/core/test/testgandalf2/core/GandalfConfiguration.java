package com.orness.gandalf.core.test.testgandalf2.core;

import com.orness.gandalf.core.test.testgandalf2.broker.GandalfBrokerBean;
import com.orness.gandalf.core.test.testgandalf2.proxy.GandalfProxyBean;
import com.orness.gandalf.core.test.testzeromq.gandalf.GandalfRoutingWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@Order
public class GandalfConfiguration {

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
        taskExecutor.execute((GandalfRoutingWorker) context.getBean("gandalfWorker"));
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
        GandalfBrokerBean gandalfBrokerBean = (GandalfBrokerBean) context.getBean("gandalfBrokerBean");
        taskExecutor.execute(gandalfBrokerBean);
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
        GandalfProxyBean gandalfProxyBean = (GandalfProxyBean) context.getBean("gandalfProxyBean");
        taskExecutor.execute(gandalfProxyBean);
    }
}