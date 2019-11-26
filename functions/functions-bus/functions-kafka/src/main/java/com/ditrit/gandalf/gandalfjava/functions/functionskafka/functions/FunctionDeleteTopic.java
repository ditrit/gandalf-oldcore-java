package com.ditrit.gandalf.gandalfjava.functions.functionskafka.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.springframework.kafka.core.KafkaAdmin;
import org.zeromq.ZMsg;

import java.util.ArrayList;
import java.util.List;

public class FunctionDeleteTopic extends Function {

    public static final String COMMAND = "DELETE_TOPIC";
    private KafkaAdmin kafkaAdmin;
    private Gson mapper;

    public FunctionDeleteTopic() {

    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        String payload = command.toArray()[14].toString();
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        String topic = jsonObject.get("topic").getAsString();

        AdminClient adminClient = AdminClient.create(this.kafkaAdmin.getConfig());
        if(!this.isTopicExist(topic, adminClient)) {
            List<String> deleteTopics = new ArrayList<>();
            deleteTopics.add(topic);
            adminClient.deleteTopics(deleteTopics);
        }
        adminClient.close();
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }

    private boolean isTopicExist(String topic, AdminClient adminClient) {
        ListTopicsResult listTopics = adminClient.listTopics();
        try {
            return  listTopics.names().get().contains(topic);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
