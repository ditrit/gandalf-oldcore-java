package com.ditrit.gandalf.tests.testgandalf2.core;

import com.ditrit.gandalf.tests.testgandalf2.broker.GandalfBrokerBean;
import com.ditrit.gandalf.tests.testgandalf2.proxy.GandalfProxyBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@Order
public class GandalfConfiguration {

    private ApplicationContext context;
    private GandalfBrokerBean gandalfBrokerBean;
    private GandalfProxyBean gandalfProxyBean;

    @Autowired
    public GandalfConfiguration(ApplicationContext context, GandalfBrokerBean gandalfBrokerBean, GandalfProxyBean gandalfProxyBean) {
        this.context = context;
        this.gandalfBrokerBean = gandalfBrokerBean;
        this.gandalfProxyBean = gandalfProxyBean;
    }

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

  /*  @Bean
    public void connectorGandalfWorkerCommand() {
        this.taskExecutor().execute((GandalfRoutingWorker) context.getBean("gandalfWorker"));
    }*/

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
        this.taskExecutor().execute(this.gandalfBrokerBean);
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

    /*@Bean
    public void proxyEvent() {
        this.taskExecutor().execute(this.gandalfProxyBean);
    }*/
}