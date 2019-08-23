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
        this.serviceClassWorkerDeque = new HashMap<>();
    }

    public void initRunnable(String routingWorkerConnector, String[] frontEndWorkerConnections, String backEndWorkerConnection) {
        this.init(routingWorkerConnector, frontEndWorkerConnections, backEndWorkerConnection);
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
            //Client
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
            //Worker
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
        String client = requestBackup.popString();
        String connector = requestBackup.popString();
        String serviceClass = requestBackup.popString();
        this.serviceClassWorkerDeque.get(serviceClass).addLast(request);
    }

    private void processWorkerResponse(ZMsg response) {
        ZMsg responseBackup = response.duplicate();
        String commandType = responseBackup.popString();
        String uuid = responseBackup.popString();
        String client = responseBackup.popString();
        String connector = responseBackup.popString();
        String serviceClass;
        if (commandType.equals(COMMAND_COMMAND_READY)) {
            serviceClass = responseBackup.popString();
            if(this.serviceClassWorkerDeque.containsKey(serviceClass)) {
                if(this.serviceClassWorkerDeque.get(serviceClass).getFirst() != null) {
                    this.sendToWorker(this.serviceClassWorkerDeque.get(serviceClass).getFirst());
                }
            }
            else {
                this.serviceClassWorkerDeque.put(serviceClass, new ArrayDeque<>());
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
