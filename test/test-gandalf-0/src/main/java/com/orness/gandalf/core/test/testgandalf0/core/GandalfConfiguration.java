package com.orness.gandalf.core.test.testgandalf0.core;

import com.orness.gandalf.core.test.testgandalf0.broker.GandalfBrokerBean;
import com.orness.gandalf.core.test.testgandalf0.proxy.GandalfProxyBean;
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
        this.taskExecutor().execute((GandalfRoutingWorker) context.getBean("gandalfWorker"));
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
        GandalfBrokerBean gandalfBrokerBean = (GandalfBrokerBean) context.getBean("gandalfBrokerBean");
        this.taskExecutor().execute(gandalfBrokerBean);
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
        GandalfProxyBean gandalfProxyBean = (GandalfProxyBean) context.getBean("gandalfProxyBean");
        this.taskExecutor().execute(gandalfProxyBean);
    }
}
