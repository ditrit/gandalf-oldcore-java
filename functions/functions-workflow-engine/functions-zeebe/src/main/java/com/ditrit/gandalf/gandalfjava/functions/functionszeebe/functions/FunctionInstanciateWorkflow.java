package com.ditrit.gandalf.gandalfjava.functions.functionszeebe.functions;

import com.ditrit.gandalf.gandalfjava.core.zeromqcore.constant.Constant;
import com.ditrit.gandalf.gandalfjava.core.zeromqcore.worker.domain.Function;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.zeebe.client.ZeebeClient;
import org.zeromq.ZMsg;

public class FunctionInstanciateWorkflow extends Function {

    private Gson mapper;
    private ZeebeClient zeebe;

    public FunctionInstanciateWorkflow() {
        super();
        this.mapper = new Gson();
    }

    @Override
    public Constant.Result executeCommand(ZMsg command) {
        String payload = command.toArray()[14].toString();
        JsonObject jsonObject = mapper.fromJson(payload, JsonObject.class);
        zeebe.newCreateInstanceCommand()
                .bpmnProcessId(jsonObject.get("id").getAsString())
                .latestVersion()
                .variables(jsonObject.get("variables").getAsString())
                .send().join();
        return null;
    }

    @Override
    public void executeEvent(ZMsg event) {

    }
}
