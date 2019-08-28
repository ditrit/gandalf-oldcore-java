package com.orness.gandalf.core.library.gandalfjavaclient;

import com.orness.gandalf.core.library.gandalfjavaclient.core.GandalfClientZeroMQ;
import com.orness.gandalf.core.library.gandalfjavaclient.core.GandalfPublisherZeroMQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.zeromq.ZMsg;

@Component
public class GandalfJavaClient {

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    private GandalfClientZeroMQ gandalfClientZeroMQ;
    private GandalfPublisherZeroMQ gandalfPublisherZeroMQ;

    @Autowired
    public GandalfJavaClient(GandalfClientZeroMQ gandalfClientZeroMQ, GandalfPublisherZeroMQ gandalfPublisherZeroMQ, ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.gandalfClientZeroMQ = gandalfClientZeroMQ;
        this.gandalfPublisherZeroMQ = gandalfPublisherZeroMQ;
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
        this.startGandalfClientZeroMQ();
    }

    private void startGandalfClientZeroMQ() {
        this.threadPoolTaskExecutor.execute(this.gandalfClientZeroMQ);
    }

    public void sendCommand(String uuid, String connector, String serviceClass, String command, String payload) {
        this.gandalfClientZeroMQ.sendCommand(uuid, connector, serviceClass, command, payload);
    }

    public ZMsg getCommandResult() {
        return this.gandalfClientZeroMQ.getLastReponses();
    }

    public void sendEvent(String topic, String typeEvent, String event) {
        this.gandalfPublisherZeroMQ.sendEvent(topic, typeEvent, event);
    }
}
