package com.orness.gandalf.core.test.testzeromq.command;

import com.google.gson.Gson;
import org.zeromq.ZMQ;
import org.zeromq.ZMsg;

import java.util.*;

import static com.orness.gandalf.core.test.testzeromq.Constant.*;

public abstract class RunnableRoutingWorker extends RoutingWorker implements Runnable {

    protected Gson mapper;
    private Map<String, Deque<ZMsg>> serviceClassWorkerDeque;

    public RunnableRoutingWorker() {
        super();
        mapper = new Gson();
    }

    public void initRunnable(String service, String[] frontEndWorkerConnections, String backEndWorkerConnection) {
        this.init(service, frontEndWorkerConnections, backEndWorkerConnection);
    }

    @Override
    public void run() {

        // Initialize poll set
        ZMQ.Poller poller = context.createPoller(2);
        poller.register(this.frontEndRoutingWorker, ZMQ.Poller.POLLIN);
        poller.register(this.backEndRoutingWorker, ZMQ.Poller.POLLIN);

        ZMsg request;
        ZMsg response;

        // Switch messages between sockets
        while (!Thread.currentThread().isInterrupted()) {

            // Client
            if (poller.pollin(0)) {
                while (true) {
                    // Receive broker message
                    request = ZMsg.recvMsg(this.frontEndRoutingWorker);
                    if (request == null) {
                        break; // Interrupted
                    }
                    // Broker it
                    this.processBrokerRequest(request);
                }
            }

            // Worker
            if (poller.pollin(1)) {
                while (true) {
                    // Receive command message
                    response = ZMsg.recvMsg(this.backEndRoutingWorker);
                    if (response == null) {
                        break; // Interrupted
                    }
                    // Broker it
                    this.processWorkerResponse(response);
                }
            }
            poller.close();
        }

        if (Thread.currentThread().isInterrupted()) {
            System.out.println("W: interrupted");
            this.close(); // interrupted
        }
    }

    private void processBrokerRequest(ZMsg request) {

        ZMsg requestBackup = request.duplicate();
        String uuid = requestBackup.popString();
        String clientIdentity = requestBackup.popString();
        String serviceClass = requestBackup.popString();
        String command = requestBackup.popString();
        String payload = requestBackup.popString();
        this.serviceClassWorkerDeque.get(serviceClass).addLast(request);
    }

    private void processWorkerResponse(ZMsg response) {

        ZMsg responseBackup = response.duplicate();
        String commandType = responseBackup.popString();
        String command;
        if (commandType.equals(COMMAND_COMMAND_READY)) {
            command = responseBackup.popString();
            if(this.serviceClassWorkerDeque.containsKey(command)) {
                if(this.serviceClassWorkerDeque.get(command).getFirst() != null) {
                    this.sendToWorker(this.serviceClassWorkerDeque.get(command).getFirst());
                }
            }
            else {
                this.serviceClassWorkerDeque.put(command, new ArrayDeque<>());
            }
        }
        else if (commandType.equals(WORKER_COMMAND_RESULT)) {
            this.sendToBroker(response);
            this.sendReadyCommand();
        }
        else {
            System.out.println("E: invalid message");
        }
        //TODO
        //msg.destroy();
    }

    public void sendToWorker(ZMsg request) {
        //Command
        request.send(this.backEndRoutingWorker);
    }

    public void sendToBroker(ZMsg response) {
        //Worker
        response.send(this.frontEndRoutingWorker);
    }
}
