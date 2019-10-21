package com.ditrit.gandalf.core.clustercore.router;

import com.ditrit.gandalf.core.zeromqcore.command.router.ClusterCommandRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.zeromq.ZMsg;

import java.util.Arrays;

import static com.ditrit.gandalf.core.clustercore.constant.ClusterConstant.CLUSTER_ROUTER_URL;

@Service
@Scope("singleton")
public class ClusterRouteur extends ClusterCommandRouter {

    private RestTemplate restTemplate;

    @Autowired
    public ClusterRouteur() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public ZMsg getCommandTarget(ZMsg command) {
        //TODO URL PARAM
        String target = this.restTemplate.getForObject(CLUSTER_ROUTER_URL, String.class);
        return this.updateCommandTarget(command, target);
    }

    private ZMsg updateCommandTarget(ZMsg command, String target) {
        //TODO TEST UPDATE
        Object[] commandArray = command.toArray();
        commandArray[3] = ""; //AGGREGATOR
        commandArray[4] = ""; //CONNECTOR
        ZMsg updatedCommand = new ZMsg();
        Arrays.stream(commandArray).forEachOrdered(e -> updatedCommand.addLast(e.toString()));

        return updatedCommand;
    }
}
