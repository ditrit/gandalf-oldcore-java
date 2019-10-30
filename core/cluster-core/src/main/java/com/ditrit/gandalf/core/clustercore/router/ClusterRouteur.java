package com.ditrit.gandalf.core.clustercore.router;

import com.ditrit.gandalf.core.zeromqcore.command.router.ClusterCommandRouter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
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
    private Gson mapper;

    @Autowired
    public ClusterRouteur() {
        this.restTemplate = new RestTemplate();
        this.mapper = new Gson();
    }

    @Override
    public ZMsg getCommandTarget(ZMsg command) {
        Object[] commandArray = command.toArray();
        String target = this.restTemplate.getForObject(CLUSTER_ROUTER_URL + "/" + commandArray[8] + "/" + commandArray[14] + "/" + commandArray[15], String.class);
        return this.updateCommandTarget(commandArray, target);
    }

    private ZMsg updateCommandTarget( Object[] command, String target) {
        JsonObject jsonTarget = this.mapper.fromJson(target, JsonObject.class);
        command[3] = jsonTarget.get("aggregator_name"); //AGGREGATOR NAME
        command[4] = jsonTarget.get("name"); //CONNECTOR NAME
        ZMsg updatedCommand = new ZMsg();
        Arrays.stream(command).forEachOrdered(e -> updatedCommand.addLast(e.toString()));

        return updatedCommand;
    }
}
